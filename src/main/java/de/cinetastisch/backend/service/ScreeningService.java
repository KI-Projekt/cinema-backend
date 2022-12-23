package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

//    @Transactional
    public Screening addScreening(String date, String timeSlot, Long movieId, Long roomId) {
        Movie movie = movieRepository.findById(movieId).get();
        Room room = roomRepository.findById(roomId).get();
        Screening savedScreening = new Screening(movie, room, date, timeSlot);
        return screeningRepository.save(savedScreening);
    }
}
