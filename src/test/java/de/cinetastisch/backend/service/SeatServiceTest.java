package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.response.SeatResponseDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.mapper.SeatMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
    @InjectMocks
    SeatService seatService;

    @Mock
    SeatRepository seatRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    SeatMapper seatMapper;

    @Test
    void getAllSeats() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        List<Seat> seatList = List.of(seat, seat);
        SeatResponseDto seatResponseDto = new SeatResponseDto(null, null, null, null);
        List<SeatResponseDto> seatResponseDtoList = List.of(seatResponseDto, seatResponseDto);
        when(roomRepository.getReferenceById((long)1.2)).thenReturn(room);
        when(seatMapper.entityToDto(seatList)).thenReturn(seatResponseDtoList);
        when(seatRepository.findAllByRoomAndCategory(room, SeatCategory.PREMIUM)).thenReturn(seatList);
        List<SeatResponseDto> response = seatService.getAllSeats((long)1.2, "PREMIUM");

        assertEquals(seatResponseDtoList, response);
    }

    @Test
    void getAllSeatsByRoom(){
        Room room = new Room( "Avengers", true, false);
        SeatResponseDto seatResponseDto = new SeatResponseDto(null, null, null, null);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        List<Seat> seatList = List.of(seat, seat);
        List<SeatResponseDto> seatResponseDtoList = List.of(seatResponseDto, seatResponseDto);
        when(roomRepository.getReferenceById((long)1.2)).thenReturn(room);
        when(seatMapper.entityToDto(seatList)).thenReturn(seatResponseDtoList);
        when(seatRepository.findAllByRoom(room)).thenReturn(seatList);
        List<SeatResponseDto> response = seatService.getAllSeats((long)1.2, null);
        assertEquals(seatResponseDtoList, response);
    }

    @Test
    void getAllSeatsByCategory(){
        Room room = new Room( "Avengers", true, false);

    }

    @Test
    void getSeat() {
    }

    @Test
    void addSeat() {
    }

    @Test
    void replaceSeat() {
    }

    @Test
    void deleteSeat() {
    }

    @Test
    void saveSeat() {
    }

    @Test
    void replaceSeats() {
    }
}