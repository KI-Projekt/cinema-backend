package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.RoomPutRequestDto;
import de.cinetastisch.backend.dto.request.RoomRequestDto;
import de.cinetastisch.backend.dto.response.RoomResponseDto;
import de.cinetastisch.backend.dto.response.RoomSlimResponseDto;
import de.cinetastisch.backend.service.RoomService;
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
class RoomControllerTest {
    @InjectMocks
    RoomController roomController;
    @Mock
    RoomService roomService;

    @Test
    void getAll() {
        RoomSlimResponseDto secondroomResponseDto = new RoomSlimResponseDto((long) 1.2, "Test", true, true);
        RoomSlimResponseDto firstroomResponseDto = new RoomSlimResponseDto((long) 1.2, "Test", true, true);
        List<RoomSlimResponseDto> responseDtoList = List.of(firstroomResponseDto, secondroomResponseDto);

        when(roomService.getAllRooms()).thenReturn(responseDtoList);

        ResponseEntity<List<RoomSlimResponseDto>> response = roomController.getAll();

        assertEquals(responseDtoList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    void getOne() {
        RoomResponseDto roomResponseDto = new RoomResponseDto((long) 1.2, "Test", true, true, null);

        when(roomService.getRoom((long) 1.2)).thenReturn(roomResponseDto);
        ResponseEntity<RoomResponseDto> response = roomController.getOne((long) 1.2);

        assertEquals(roomResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void add() {
        RoomResponseDto roomResponseDto = new RoomResponseDto((long) 1.2, "Test", true, true, null);
        RoomRequestDto roomRequestDto = new RoomRequestDto("Test", "true", "true", 3, 3);

        when(roomService.addRoom(roomRequestDto)).thenReturn(roomResponseDto);

        ResponseEntity<?> response = roomController.add(roomRequestDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void replaceRoom() {
        RoomResponseDto roomResponseDto = new RoomResponseDto((long) 1.2, "Test", true, true, null);

        RoomPutRequestDto roomRequestDto = new RoomPutRequestDto((long) 1.2,"Test", "true", "true", null);
        when(roomService.replaceRoom((long) 1.2, roomRequestDto)).thenReturn(roomResponseDto);
        ResponseEntity<?> response = roomController.replaceRoom((long) 1.2, roomRequestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void deleteOne() {

        ResponseEntity<?> response = roomController.deleteOne((long)1.2);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}