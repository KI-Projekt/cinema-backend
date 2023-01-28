package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.exception.NoResourcesException;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper mapper;
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
        if(result.size() == 0){
            throw new NoResourcesException("No Screenings given");
        }

        return mapper.trimDto(mapper.entityToDto(result));
    }

    public ScreeningFullResponseDto getScreening(Long id){
        return mapper.entityToDto(screeningRepository.getReferenceById(id));
    }

    @Transactional
    public ScreeningFullResponseDto addScreening(ScreeningRequestDto screeningRequestDto) {
        Screening screening = mapper.dtoToEntity(screeningRequestDto);
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

        if(screening.isThreeD() && !screening.getRoom().getHasThreeD()){
            throw new IllegalArgumentException("Selected Room doesn't support 3D");
        }

        if(screening.isDolbyAtmos() && !screening.getRoom().getHasDolbyAtmos()){
            throw new IllegalArgumentException("Selected Room doesn't support Dolby Atmos");
        }

        return mapper.entityToDto(screeningRepository.save(screening));
    }

    public ScreeningFullResponseDto replaceScreening(Long id, ScreeningRequestDto screeningDto) {
        Screening oldScreening = screeningRepository.getReferenceById(id);
        Screening newScreening = mapper.dtoToEntity(screeningDto);
        newScreening.setId(oldScreening.getId());
        return mapper.entityToDto(screeningRepository.save(newScreening));
    }

    @Transactional
    public void deleteScreening(Long id){
        Screening screening = screeningRepository.getReferenceById(id);
        screeningRepository.delete(screening);
    }

    public LocalDateTime calculateEndDateTime(LocalDateTime start, Movie movie){
        LocalDateTime newTime = start.plusMinutes(Long.parseLong(movie.getRuntime().split(" ")[0]));
        return newTime.plusMinutes(30L); // + Ads + Cleaning
    }

    @Transactional
    public ScreeningFullResponseDto cancelScreening(Long id) {
        Screening screening = screeningRepository.getReferenceById(id);

        if(screening.getStatus() == ScreeningStatus.CANCELLED){
            throw new ResourceAlreadyExistsException("Screening is already cancelled");
        }

        screening.setStatus(ScreeningStatus.CANCELLED);
        screeningRepository.save(screening);

        return mapper.entityToDto(screening);
    }
}
