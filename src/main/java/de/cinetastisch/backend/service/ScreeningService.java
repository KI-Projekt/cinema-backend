package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.ScreeningResponseDto;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.dto.ScreeningRequestDto;
import de.cinetastisch.backend.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper mapper;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository, ScreeningMapper mapper) {
        this.screeningRepository = screeningRepository;
        this.mapper = mapper;
    }

    public List<ScreeningResponseDto> getAllScreenings(String startTime, Long movieId){
        if ((startTime != null && !startTime.isBlank()) && (movieId != null)){

        }

        if (startTime != null && !startTime.isBlank()){
            return mapper.entityToDto(screeningRepository.findAllAfterStartDateTime(LocalDateTime.parse(startTime)));
        }
        return mapper.entityToDto(screeningRepository.findAll());
    }

    public ScreeningResponseDto getScreening(Long id){
        return mapper.entityToDto(screeningRepository.getReferenceById(id));
    }

    @Transactional
    public ScreeningResponseDto addScreening(ScreeningRequestDto screeningRequestDto) {
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

    public ScreeningResponseDto replaceScreening(Long id, ScreeningRequestDto screeningDto) {
        Screening oldScreening = mapper.toEntity(id);
        Screening newScreening = mapper.dtoToEntity(screeningDto);
        newScreening.setId(oldScreening.getId());
        return mapper.entityToDto(screeningRepository.save(newScreening));
    }

    public void deleteScreening(Long id){
        Screening screening = mapper.toEntity(id);
        screeningRepository.delete(screening);
    }

    public LocalDateTime calculateEndDateTime(LocalDateTime start, Movie movie){
        LocalDateTime newTime = start.plusMinutes(Long.parseLong(movie.getRuntime().split(" ")[0]));
        return newTime.plusMinutes(30L); // + Ads + Cleaning
    }

}
