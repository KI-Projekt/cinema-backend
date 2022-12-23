package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.dto.ScreeningDto;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;


    public List<ScreeningDto> getAllScreenings(){
        List<Screening> screenings = screeningRepository.findAll();
        List<ScreeningDto> screeningDtos = new ArrayList<>();

        for (Screening s : screenings){
            screeningDtos.add(convertToDto(s));
        }

        return screeningDtos;
    }

    public ScreeningDto getScreening(Long id){
        Screening screening = screeningRepository.findById(id).get();
        return convertToDto(screening);
    }

    public Screening addScreening(String date, String timeSlot, Long movieId, Long roomId) {
        Movie movie = movieRepository.findById(movieId).get();
        Room room = roomRepository.findById(roomId).get();
        Screening savedScreening = new Screening(movie, room, date, timeSlot);
        return screeningRepository.save(savedScreening);
    }

    public ScreeningDto convertToDto(Screening screening){
        ScreeningDto screeningDTO = new ScreeningDto();
        screeningDTO.setId(screening.getId());
        screeningDTO.setRoom(screening.getRoom());
        screeningDTO.setDate(screening.getDate());
        screeningDTO.setMovie(screening.getMovie());
        screeningDTO.setTimeSlot(screening.getTimeSlot());
        return screeningDTO;
    }
}
