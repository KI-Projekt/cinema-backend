package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.RoomPutRequestDto;
import de.cinetastisch.backend.dto.request.RoomRequestDto;
import de.cinetastisch.backend.dto.request.SeatPutRequestDto;
import de.cinetastisch.backend.dto.response.RoomResponseDto;
import de.cinetastisch.backend.dto.response.RoomSlimResponseDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.exception.NoResourcesException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.RoomMapper;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ListResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @InjectMocks
    RoomService roomService;

    @Mock
    RoomRepository roomRepository;

    @Mock
    SeatRepository seatRepository;

    @Mock
    RoomMapper roomMapper;

    @Test
    void getAllRooms() {
        Room room = new Room( "Avengers", true, false);
        List<Room> roomList = List.of(room,room);
        RoomResponseDto roomResponseDto = new RoomResponseDto(null,null,true,true,null);
        List<RoomResponseDto> responseDtoList = List.of(roomResponseDto,roomResponseDto);
        RoomSlimResponseDto roomSlimResponseDto = new RoomSlimResponseDto(null,null,null,true);
        List<RoomSlimResponseDto> roomSlimResponseDtoList = List.of(roomSlimResponseDto,roomSlimResponseDto);
        when(roomRepository.findAll()).thenReturn(roomList);
        when(roomMapper.entityToDto(roomList)).thenReturn(responseDtoList);
        when(roomMapper.dtoToSlimDto(responseDtoList)).thenReturn(roomSlimResponseDtoList);

        List<RoomSlimResponseDto> respone = roomService.getAllRooms();

        assertEquals(roomSlimResponseDtoList,respone);

    }
    @Test
    void getAllRoomsExeption(){

        List<Room> roomList = List.of();
        when(roomRepository.findAll()).thenReturn(roomList);
        assertThrows(NoResourcesException.class,()->roomService.getAllRooms());



    }

    @Test
    void getRoom() {
        Room room = new Room( "Avengers", true, false);
        RoomResponseDto roomResponseDto = new RoomResponseDto(null,null,true,true,null);
        when(roomRepository.getReferenceById((long)1.2)).thenReturn(room);
        when(roomMapper.entityToDto(room)).thenReturn(roomResponseDto);

        RoomResponseDto response = roomService.getRoom((long)1.2);

        assertEquals(roomResponseDto,response);


    }

    @Test
    void addRoomExeption() {
        RoomRequestDto requestDto = new RoomRequestDto(null,null,null,-3,2);
        assertThrows(IllegalArgumentException.class,()->roomService.addRoom(requestDto));
    }
    @Test
    void addRoom() {
        Room room = new Room( "Avengers", true, false);
        RoomResponseDto roomResponseDto = new RoomResponseDto(null,null,true,true,null);
        Seat seat = new Seat(room,1,1,SeatCategory.NORMAL);
        List<Seat> seatList = List.of(seat);
        room.setSeats(seatList);
        RoomRequestDto requestDto = new RoomRequestDto(null,null,null,1,1);
        when(roomMapper.dtoToEntity(requestDto)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(roomMapper.entityToDto(room)).thenReturn(roomResponseDto);

        RoomResponseDto respone = roomService.addRoom(requestDto);
        assertEquals(roomResponseDto,respone);



    }

    @Test
    void replaceRoomExeption() {
        RoomResponseDto roomResponseDto = new RoomResponseDto(null,null,true,true,null);
        RoomPutRequestDto putRequestDto = new RoomPutRequestDto(null,null,null,null,null);
        assertThrows(IllegalArgumentException.class,()->roomService.replaceRoom((long)1.2,putRequestDto));
    }
    @Test
    void replaceRoom() {

        SeatPutRequestDto seatPutRequestDto =new SeatPutRequestDto((long)1.2,"PREMIUM");
        List<SeatPutRequestDto> seatPutRequestDtos = List.of(seatPutRequestDto);
        Room room = new Room( "Avengers", false, false);
        room.setId((long)1.2);
        Seat seat = new Seat(room,2,2, SeatCategory.PREMIUM);
        seat.setId((long)1.2);
        List<Seat> seatList = List.of();
        RoomResponseDto roomResponseDto = new RoomResponseDto(null,null,true,true,null);
        RoomPutRequestDto putRequestDto = new RoomPutRequestDto((long)1.2,"Avengers","false","flase",seatPutRequestDtos);
        when(roomRepository.getReferenceById((long)1.2)).thenReturn(room);
        when(roomMapper.entityToDto(room)).thenReturn(roomResponseDto);
        when(roomRepository.save(room)).thenReturn(room);

        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);



        RoomResponseDto response = roomService.replaceRoom((long)1.2,putRequestDto);

        assertEquals(roomResponseDto,response);
    }

    @Test
    void deleteRoomExeption() {
        when(roomRepository.existsById((long)1.2)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,()->roomService.deleteRoom((long)1.2));

    }
    @Test
    void deleteRoom() {
        when(roomRepository.existsById((long)1.2)).thenReturn(true);

        roomService.deleteRoom((long)1.2);

    }
}