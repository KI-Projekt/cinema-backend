package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceAlreadyReservedException;
import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
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

    public List<Screening> getAllScreenings(){
        List<Screening> screenings = screeningRepository.findAll();
        return screenings;
    }

    public Screening getScreening(Long id){
        Screening screening = screeningRepository.findById(id).get();
        return screening;
    }

    public Screening addScreening(Long movieId, Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        Movie movie = movieRepository.findById(movieId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        Room room = roomRepository.findById(roomId)
                                  .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        List<Screening> runningScreenings = screeningRepository.findAllByRoomAndTime(roomId, startTime, endTime);
        if(runningScreenings.size() != 0){
            throw new ResourceAlreadyReservedException("Screenings " + runningScreenings +
                                                               " already occupy the room at the same time.");
        }

        return screeningRepository.save(new Screening(movie, room, startTime, endTime));
    }

    public List<Screening> getAllScreeningsBetweenTimespan(LocalDateTime from, LocalDateTime to){
        return screeningRepository.findAllByLocalDateTimes(from, to);
    }

    public List<Screening> checkRoomForReservations(Long roomId, LocalDateTime from, LocalDateTime to){
        roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("RoomID not found"));
        return screeningRepository.findAllByRoomAndTime(roomId, from, to);
    }
}
