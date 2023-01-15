package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.*;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.mapper.SeatMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.*;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper mapper;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final MovieRepository movieRepository;

    public List<ScreeningResponseDto> getAllScreenings(String startTime, Long movieId ){
        List<Screening> result;
        if (movieId != null && startTime != null){
            Movie movie = movieRepository.getReferenceById(movieId);
            result = screeningRepository.findAllByMovieAndStartDateTimeAfter(movie, LocalDateTime.parse(startTime));
        } else if (movieId != null){
            Movie movie = movieRepository.getReferenceById(movieId);
            result = screeningRepository.findAllByMovie(movie);
        } else if (startTime != null && !startTime.isBlank()){
            result = screeningRepository.findAllByStartDateTimeAfter(LocalDateTime.parse(startTime));
        } else {
            result = screeningRepository.findAll();
        }
        return mapper.trimDto(mapper.entityToDto(result));
    }

    public ScreeningFullResponseDto getScreening(Long id){
        return mapper.entityToDto(screeningRepository.getReferenceById(id));
    }

    @Transactional
    public ScreeningFullResponseDto addScreening(ScreeningRequestDto screeningRequestDto) {
        System.out.println(screeningRequestDto);
        Screening screening = mapper.dtoToEntity(screeningRequestDto);
        System.out.println(screening);
        if (screeningRequestDto.endDateTime() == null){
            screening.setEndDateTime(calculateEndDateTime(screening.getStartDateTime(), screening.getMovie()));
        }

        // Check if room is already occupied for that time
        List<Screening> runningScreenings = screeningRepository.findAllByRoomAndTime(screening.getRoom(),
                                                                                     screening.getStartDateTime(),
                                                                                     screening.getEndDateTime());
        if(runningScreenings.size() != 0){
            throw new ResourceAlreadyOccupiedException("Screenings " + runningScreenings + " already occupy the room for that time.");
        }

        return mapper.entityToDto(screeningRepository.save(screening));
    }

    public ScreeningFullResponseDto replaceScreening(Long id, ScreeningRequestDto screeningDto) {
        Screening oldScreening = screeningRepository.getReferenceById(id);
        Screening newScreening = mapper.dtoToEntity(screeningDto);
        newScreening.setId(oldScreening.getId());
        return mapper.entityToDto(screeningRepository.save(newScreening));
    }

    public void deleteScreening(Long id){
        Screening screening = screeningRepository.getReferenceById(id);
        screeningRepository.delete(screening);
    }

    public LocalDateTime calculateEndDateTime(LocalDateTime start, Movie movie){
        LocalDateTime newTime = start.plusMinutes(Long.parseLong(movie.getRuntime().split(" ")[0]));
        return newTime.plusMinutes(30L); // + Ads + Cleaning
    }

    @Named("generateSeatingPlan")
    public ScreeningRoomPlanResponseDto getSeatingPlan(Long id) {
        Screening screening = screeningRepository.getReferenceById(id);
        Room room = screening.getRoom();
        List<ScreeningSeatRowDto> screeningSeatRowDtos = new ArrayList<>();

        Integer rows = seatRepository.findAllByRoomOrderByRowDesc(room).get(0).getRow();
        for(int i = 1; i <= rows; i++){

            List<Seat> seatsByRow = seatRepository.findAllByRoomAndRowOrderByColumnDesc(room, i);
            List<ScreeningSeatDto> screeningSeatDtos = new ArrayList<>();
            if(seatsByRow == null || seatsByRow.size() == 0){
                screeningSeatRowDtos.add(new ScreeningSeatRowDto("Row " + i + " (empty)", screeningSeatDtos));
                continue;
            }
            for (Seat s : seatsByRow) {
                boolean isReserved = reservationRepository.existsByScreeningAndSeatAndExpiresAtIsGreaterThanEqual(screening, s, LocalDateTime.now())
                        || ticketRepository.existsByScreeningAndSeat(screening, s);
                screeningSeatDtos.add(new ScreeningSeatDto(seatMapper.entityToDto(s),isReserved));
            }

            screeningSeatRowDtos.add(new ScreeningSeatRowDto("Row " + i, screeningSeatDtos));
        }
        return new ScreeningRoomPlanResponseDto(id, room.getId(), screeningSeatRowDtos);
    }
}
