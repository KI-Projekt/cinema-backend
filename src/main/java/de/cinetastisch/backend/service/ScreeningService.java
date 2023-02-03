package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper mapper;

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

    public List<ScreeningResponseDto> getAllScreenings(Specification<Screening> spec, Sort sort) {
        if(sort == null){
            sort = Sort.by("startDateTime").ascending();
        }

        List<Screening> result = screeningRepository.findAll(spec, sort);
        return mapper.trimDto(mapper.entityToDto(result));
    }
}
