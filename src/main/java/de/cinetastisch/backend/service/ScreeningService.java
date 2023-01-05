package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.ScreeningDto;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.dto.ScreeningRequestDto;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    private final ScreeningMapper mapper;

    public List<Screening> getAllScreenings(String ldtStart){
        if (ldtStart != null && !ldtStart.isBlank()){
            return screeningRepository.findAllAfterStartDateTime(LocalDateTime.parse(ldtStart));
        }
        return screeningRepository.findAll();
    }

    public Screening getScreening(Long id){
        return screeningRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Screening Not Found"));
    }

    public Screening addScreening(ScreeningRequestDto screeningRequestDto) {
        Movie movie;
        Room room;

        // Check if Movie exists
        if(screeningRequestDto.movieId() != null && screeningRequestDto.movieId().describeConstable().isPresent()){
            movie = movieRepository.findById(screeningRequestDto.movieId())
                                   .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        } else {
            throw new ResourceNotFoundException("Movie not found");
        }

        // Check if Room exists
        if(screeningRequestDto.roomId() != null && screeningRequestDto.roomId().describeConstable().isPresent()){
            room = roomRepository.findById(screeningRequestDto.roomId())
                                 .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        } else {
            throw new ResourceNotFoundException("Room not found");
        }

        // Check if room is already occupied for that time
        List<Screening> runningScreenings = screeningRepository.findAllByRoomAndTime(room.getId(),
                                                                                     screeningRequestDto.startDateTime(),
                                                                                     screeningRequestDto.endDateTime());
        if(runningScreenings.size() != 0){
            throw new ResourceAlreadyOccupiedException("Screenings " + runningScreenings + " already occupy the room for that time.");
        }

        return screeningRepository.save(new Screening(movie, room, screeningRequestDto.startDateTime(), screeningRequestDto.endDateTime()));
    }

    public List<Screening> getAllScreeningsBetweenTimespan(LocalDateTime from, LocalDateTime to){
        return screeningRepository.findAllByLocalDateTimes(from, to);
    }

    public List<Screening> checkRoomForReservations(Long roomId, LocalDateTime from, LocalDateTime to){
        roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("RoomID not found"));
        return screeningRepository.findAllByRoomAndTime(roomId, from, to);
    }

    public Screening replaceScreening(Long id, ScreeningRequestDto screeningDto) {
        Screening oldScreening = getScreening(id);
        Screening newScreening = mapper.screeningRequestDtoToEntity(screeningDto);
        newScreening.setId(oldScreening.getId());
        return screeningRepository.save(newScreening);
    }

    public void deleteScreening(Long id){
        Screening screening = getScreening(id);
        screeningRepository.delete(screening);
    }


}
