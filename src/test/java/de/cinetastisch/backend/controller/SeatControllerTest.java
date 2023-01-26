package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.SeatDto;
import de.cinetastisch.backend.dto.SeatResponseDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.service.SeatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
@ExtendWith(MockitoExtension.class)
class SeatControllerTest {
    @InjectMocks
    SeatController seatController;
    @Mock
    SeatService seatService;

    @Test
    void getAll() {
        SeatResponseDto firstseatResponseDto = new SeatResponseDto(null,null,null,null);
        SeatResponseDto secondseatResponseDto = new SeatResponseDto(null,null,null,null);
        List<SeatResponseDto> responseDtoList = List.of(firstseatResponseDto,secondseatResponseDto);


        when(seatService.getAllSeats((long)1.2,"Premium")).thenReturn(responseDtoList);

        ResponseEntity<?> response = seatController.getAll((long)1.2,"Premium");
        assertEquals(responseDtoList,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void getOne() {
        SeatResponseDto seatResponseDto = new SeatResponseDto(null,null,null,null);
        SeatDto seatDto = new SeatDto(null,null,null,null,null);


        when(seatService.replaceSeat((long)1.2,seatDto)).thenReturn(seatResponseDto);

        ResponseEntity<?> response = seatController.getOne((long)1.2,seatDto);
        assertEquals(seatResponseDto,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    void addOne() {
        SeatDto seatDto = new SeatDto(null,null,null,null,null);
        SeatResponseDto seatResponseDto = new SeatResponseDto(null,null,null,null);
        when(seatService.addSeat(seatDto)).thenReturn(seatResponseDto);
        ResponseEntity<?> response = seatController.addOne(seatDto);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(seatResponseDto,response.getBody());
    }

    @Test
    void GetOnebyId() {
        SeatResponseDto seatResponseDto = new SeatResponseDto(null,null,null,null);



        when(seatService.getSeat((long)1.2)).thenReturn(seatResponseDto);

        ResponseEntity<?> response = seatController.getOne((long)1.2);
        assertEquals(seatResponseDto,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());




    }
}