package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.ScreeningRepository;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import org.springframework.data.jpa.domain.Specification;

//import java.awt.print.Pageable;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @InjectMocks
    ScreeningService screeningService;

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    ScreeningMapper screeningMapper;
    final Specification<Screening> spec = null;
    final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");


    @Test
    void getAllScreenings() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        List<Screening> screeningList = List.of(screening, screening);
        Page<Screening> screeningPage = new PageImpl<>(List.of(screening, screening), pageable, 0);
        ScreeningResponseDto screeningResponseDto = new ScreeningResponseDto(null, null, null, null, null, null, true, true, null);
        List<ScreeningResponseDto> screeningResponseDtoList = List.of(screeningResponseDto, screeningResponseDto);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null );
        List<ScreeningFullResponseDto> screeningFullResponseDtoList = List.of(screeningFullResponseDto, screeningFullResponseDto);
        when(screeningRepository.findAll(spec, pageable)).thenReturn(screeningPage);
        when(screeningMapper.entityToDto(screeningPage)).thenReturn(screeningFullResponseDtoList);
        when(screeningMapper.trimDto(screeningFullResponseDtoList)).thenReturn(screeningResponseDtoList);
        List<ScreeningResponseDto> response = screeningService.getAllScreenings(spec, pageable);

        assertEquals(screeningResponseDtoList, response);
    }

    @Test
    void getScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null );
        List<ScreeningFullResponseDto> screeningFullResponseDtoList = List.of(screeningFullResponseDto, screeningFullResponseDto);
        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(screeningMapper.entityToDto(screening)).thenReturn(screeningFullResponseDto);
        ScreeningFullResponseDto response = screeningService.getScreening((long)1.2);
        assertEquals(screeningFullResponseDto, response);
    }

    @Test
    void addScreening() {
        //Screening screening = new Screening(null, null, null, null, null, true, true, null);
        //ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null );
        //ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto(null, null, null, null, null, null, null);

        //when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);

    }

    @Test
    void replaceScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto(null, null, null, null, null, null, null);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null );
        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.save(screening)).thenReturn(screening);
        when(screeningMapper.entityToDto(screening)).thenReturn(screeningFullResponseDto);
        ScreeningFullResponseDto response = screeningService.replaceScreening((long)1.2, screeningRequestDto);

        assertEquals(screeningFullResponseDto, response);
    }

    @Test
    void deleteScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        screeningService.deleteScreening((long)1.2);
    }

    @Test
    void calculateEndDateTime() {

    }

    @Test
    void cancelScreening() {
    }
}