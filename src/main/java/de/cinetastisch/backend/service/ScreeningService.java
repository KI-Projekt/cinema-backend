package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.info.ScreeningInfo;
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
        return screeningRepository.findAll();
    }

    public Screening getScreening(Long id){
        return screeningRepository.findById(id).get();
    }

    public Screening addScreening(ScreeningInfo screeningInfo) {
        Movie movie;
        Room room;

        // Check if Movie exists
        if(screeningInfo.movieId() != null && screeningInfo.movieId().describeConstable().isPresent()){
            movie = movieRepository.findById(screeningInfo.movieId())
                                   .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        } else if (screeningInfo.movieTitle() != null && screeningInfo.movieTitle().describeConstable().isPresent()){
            movie = movieRepository.findByTitle(screeningInfo.movieTitle())
                                   .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        } else {
            throw new ResourceNotFoundException("Movie not found");
        }

        // Check if Room exists
        if(screeningInfo.roomId() != null && screeningInfo.roomId().describeConstable().isPresent()){
            room = roomRepository.findById(screeningInfo.roomId())
                                 .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        } else if (screeningInfo.roomName() != null && screeningInfo.roomName().describeConstable().isPresent()){
            room = roomRepository.findByName(screeningInfo.roomName())
                                 .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        } else {
            throw new ResourceNotFoundException("Room not found");
        }

        // Check if room is already occupied for that time
        List<Screening> runningScreenings = screeningRepository.findAllByRoomAndTime(room.getId(),
                                                                                     screeningInfo.startTime(),
                                                                                     screeningInfo.endTime());
        if(runningScreenings.size() != 0){
            throw new ResourceAlreadyOccupiedException("Screenings " + runningScreenings +
                                                               " already occupy the room for that time.");
        }

        return screeningRepository.save(new Screening(movie, room, screeningInfo.startTime(), screeningInfo.endTime()));
    }

    public List<Screening> getAllScreeningsBetweenTimespan(LocalDateTime from, LocalDateTime to){
        return screeningRepository.findAllByLocalDateTimes(from, to);
    }

    public List<Screening> checkRoomForReservations(Long roomId, LocalDateTime from, LocalDateTime to){
        roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("RoomID not found"));
        return screeningRepository.findAllByRoomAndTime(roomId, from, to);
    }
}
