package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.dto.ScreeningRequestDto;
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
    private final MovieService movieService;
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
        Screening screening = mapper.dtoToEntity(screeningRequestDto);

        // Check if room is already occupied for that time
        List<Screening> runningScreenings = screeningRepository.findAllByRoomAndTime(screening.getRoom(),
                                                                                     screening.getStartDateTime(),
                                                                                     screening.getEndDateTime());
        if(runningScreenings.size() != 0){
            throw new ResourceAlreadyOccupiedException("Screenings " + runningScreenings + " already occupy the room for that time.");
        }

        return screeningRepository.save(screening);
    }

    public List<Screening> getAllScreeningsBetweenTimespan(LocalDateTime from, LocalDateTime to){
        return screeningRepository.findAllByLocalDateTimes(from, to);
    }

    public List<Screening> checkRoomForReservations(Long roomId, LocalDateTime from, LocalDateTime to){
        Room room = roomRepository.getReferenceById(roomId);
        return screeningRepository.findAllByRoomAndTime(room, from, to);
    }

    public Screening replaceScreening(Long id, ScreeningRequestDto screeningDto) {
        Screening oldScreening = getScreening(id);
        Screening newScreening = mapper.dtoToEntity(screeningDto);
        newScreening.setId(oldScreening.getId());
        return screeningRepository.save(newScreening);
    }

    public void deleteScreening(Long id){
        Screening screening = getScreening(id);
        screeningRepository.delete(screening);
    }

    public LocalDateTime calculateEndDateTime(LocalDateTime start, Long movieId){
        Movie movie = movieService.getMovie(movieId);
        LocalDateTime newTime = start.plusMinutes(Long.parseLong(movie.getRuntime().split(" ")[0]));
        return newTime.plusMinutes(30L); // + Ads + Cleaning
    }

}
