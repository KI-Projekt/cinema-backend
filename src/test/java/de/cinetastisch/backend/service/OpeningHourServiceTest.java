package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.OpeningHourRequestDto;
import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.repository.OpeningHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class OpeningHourServiceTest {
    @InjectMocks
    OpeningHourService openingHourService;

    @Mock
    OpeningHourRepository openingHourRepository;

    LocalTime localTime = LocalTime.of(12,12,12);


    @Test
    void getAllOpeningHours() {
        OpeningHour openingHour = new OpeningHour(null, null, null);
        OpeningHour openingHour2 = new OpeningHour(null, null, null);

        List<OpeningHour> openingHourList = List.of(openingHour, openingHour2);

        when(openingHourRepository.findAll()).thenReturn(openingHourList);

        List<OpeningHour> respone = openingHourService.getAllOpeningHours();
        assertEquals(openingHourList,respone);
    }

    @Test
    void replaceOpeningHour() {
        OpeningHour oldOpeningHour = new OpeningHour(null, localTime, localTime);
        OpeningHourRequestDto requestDto =new OpeningHourRequestDto("12:12:12","12:12:12");
        when(openingHourRepository.getReferenceById((long)1.2)).thenReturn(oldOpeningHour);
        when(openingHourRepository.save(oldOpeningHour)).thenReturn(oldOpeningHour);

        OpeningHour response = openingHourService.replaceOpeningHour((long)1.2,requestDto);

        assertEquals(oldOpeningHour, response);
    }
}