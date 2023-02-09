package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.service.ScreeningService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScreeningControllerTest {
    @InjectMocks
    ScreeningController screeningController;
    @Mock
    ScreeningService screeningService;


    @Test
    void getAll(){
        ScreeningResponseDto responseDto = new ScreeningResponseDto(null,null,null,null,null,null,true, false, null);
        List<ScreeningResponseDto> responseDtoList = List.of(responseDto,responseDto);
        when(screeningService.getAllScreenings(null,null)).thenReturn(responseDtoList);
        ResponseEntity<?> response = screeningController.getAll(null,null);

        assertEquals(responseDtoList,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    void getOne() {
        ScreeningFullResponseDto screeningResponseDto = new ScreeningFullResponseDto((long)1.2, null, null, null, null, null, null, false, false, null);

        when(screeningService.getScreening((long)1.2)).thenReturn(screeningResponseDto);

        ResponseEntity<ScreeningFullResponseDto> response = screeningController.getOne((long)1.2);
        assertEquals(screeningResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void add() {
        ScreeningRequestDto requestDto = new ScreeningRequestDto(null,null,null,null,"false", "false", null);
        ScreeningFullResponseDto responseDto = new ScreeningFullResponseDto(null,null,null,null,null,null,null, false, false, null);
        when(screeningService.addScreening(requestDto)).thenReturn(responseDto);

        ResponseEntity<?> response = screeningController.add(requestDto);
        assertEquals(responseDto,response.getBody());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    void replaceOne() {
        ScreeningRequestDto requestDto = new ScreeningRequestDto(null,null,null,null,"false", "false", null);
        ScreeningFullResponseDto responseDto = new ScreeningFullResponseDto(null,null,null,null,null,null,null,false, false, null);

        when(screeningService.replaceScreening((long)1.2, requestDto)).thenReturn(responseDto);
        ResponseEntity<?> response = screeningController.replaceOne(requestDto,(long)1.2);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void cancalScreening(){
        ScreeningFullResponseDto responseDto = new ScreeningFullResponseDto(null,null,null,null,null,null,null, false, false, null);
        when(screeningService.cancelScreening((long)1.2)).thenReturn(responseDto);

        ResponseEntity<?> response = screeningController.cancelScreening((long)1.2);

        assertEquals(responseDto,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    void delete() {
        ResponseEntity<?> response = screeningController.delete((long)1.2);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

    }


}