package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public Screening addScreening(String date, String timeSlot, Long movieId, Long roomId) {
        Movie movie = movieRepository.findById(movieId).get();
        Room room = roomRepository.findById(roomId).get();
        Screening savedScreening = new Screening(movie, room, LocalDate.parse("2023-01-01"), LocalTime.parse("08:00:00"), LocalTime.parse("09:00:00"));
        return screeningRepository.save(savedScreening);
    }
}
