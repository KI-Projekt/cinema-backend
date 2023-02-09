package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.OpeningHourRequestDto;
import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.service.OpeningHourService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpeningHourControllerTest {

    @InjectMocks
    OpeningHourController openingHourController;

    @Mock
    OpeningHourService openingHourService;
    private ResponseEntity<?> response;


    @Test
    void getAll() {
        OpeningHour openingHour = new OpeningHour(DayOfWeek.FRIDAY, null, null);
        List<OpeningHour> openingHourList = List.of(openingHour, openingHour);
        when(openingHourService.getAllOpeningHours()).thenReturn(openingHourList);

        ResponseEntity<?> response = openingHourController.getAll();

        assertEquals(openingHourList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    void replace() {
        OpeningHour openingHour = new OpeningHour(DayOfWeek.FRIDAY, null, null);
        OpeningHourRequestDto requestDto = new OpeningHourRequestDto(null, null);


        when(openingHourService.replaceOpeningHour((long) 1.2, requestDto)).thenReturn(openingHour);

        ResponseEntity<?> response = openingHourController.replace((long) 1.2, requestDto);

        assertEquals(openingHour, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }
}