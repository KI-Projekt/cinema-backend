package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.SeatRequestDto;
import de.cinetastisch.backend.dto.response.SeatResponseDto;
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
        SeatResponseDto firstseatResponseDto = new SeatResponseDto(null, null, null, null);
        SeatResponseDto secondseatResponseDto = new SeatResponseDto(null,null,null,null);
        List<SeatResponseDto> responseDtoList = List.of(firstseatResponseDto,secondseatResponseDto);


        when(seatService.getAllSeats((long)1.2,"Premium")).thenReturn(responseDtoList);

        ResponseEntity<?> response = seatController.getAll((long)1.2,"Premium");
        assertEquals(responseDtoList,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void addOne() {
        SeatRequestDto seatDto = new SeatRequestDto(null, null, null, null, null);
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
    @Test
    void replaceMultiple(){
        SeatRequestDto seatRequestDto = new SeatRequestDto((long)1.2,(long)1.2,SeatCategory.NORMAL,2,3);
        SeatResponseDto seatResponseDto = new SeatResponseDto((long)1.2,SeatCategory.NORMAL,2,2);
        List<SeatResponseDto> responseDtoList = List.of(seatResponseDto,seatResponseDto);
        List<SeatRequestDto> requestDtoList = List.of(seatRequestDto,seatRequestDto);
        when(seatService.replaceSeats(requestDtoList)).thenReturn(responseDtoList);

        ResponseEntity<?> response = seatController.replaceMultiple(requestDtoList);

        assertEquals(responseDtoList, response.getBody());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());

    }
    @Test
    void replaceOne(){
        SeatRequestDto seatRequestDto = new SeatRequestDto((long)1.2,(long)1.2,SeatCategory.NORMAL,2,3);
        SeatResponseDto seatResponseDto = new SeatResponseDto((long)1.2,SeatCategory.NORMAL,2,2);

        when(seatService.replaceSeat((long)1.2,seatRequestDto)).thenReturn(seatResponseDto);

        ResponseEntity<?> response = seatController.replaceOne((long)1.2,seatRequestDto);

        assertEquals(seatResponseDto, response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        }
}