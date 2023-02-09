package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.SeatRequestDto;
import de.cinetastisch.backend.dto.response.SeatResponseDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
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
        SeatResponseDto seatResponseDto = new SeatResponseDto(null, null, null, null);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        List<Seat> seatList = List.of(seat, seat);
        List<SeatResponseDto> seatResponseDtoList = List.of(seatResponseDto, seatResponseDto);
        when(seatMapper.entityToDto(seatList)).thenReturn(seatResponseDtoList);
        when(seatRepository.findAllByCategory(SeatCategory.PREMIUM)).thenReturn(seatList);

        List<SeatResponseDto> response = seatService.getAllSeats(null, "PREMIUM");
        assertEquals(seatResponseDtoList, response);
    }

    @Test
    void getAllSeatsByNothing(){
        Room room = new Room( "Avengers", true, false);
        SeatResponseDto seatResponseDto = new SeatResponseDto(null, null, null, null);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        List<Seat> seatList = List.of(seat, seat);
        List<SeatResponseDto> seatResponseDtoList = List.of(seatResponseDto, seatResponseDto);
        when(seatMapper.entityToDto(seatList)).thenReturn(seatResponseDtoList);
        when(seatRepository.findAll()).thenReturn(seatList);

        List<SeatResponseDto> response = seatService.getAllSeats(null, null);
        assertEquals(seatResponseDtoList, response);
    }

    @Test
    void getSeat() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        SeatResponseDto seatResponseDto = new SeatResponseDto(null, null, null, null);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        when(seatMapper.entityToDto(seat)).thenReturn(seatResponseDto);
        SeatResponseDto response = seatService.getSeat((long)1.2);

        assertEquals(seatResponseDto, response);
    }

    @Test
    void addSeat() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        SeatResponseDto seatResponseDto = new SeatResponseDto((long)1.2, SeatCategory.PREMIUM, 1, 2);
        SeatRequestDto seatRequestDto = new SeatRequestDto((long)1.2, (long)1.3, SeatCategory.PREMIUM, 1,2);
        when(seatMapper.dtoToEntity(seatRequestDto)).thenReturn(seat);
        when(seatMapper.entityToDto(seat)).thenReturn(seatResponseDto);
        when(seatService.saveSeat(seat)).thenReturn(seat);

        SeatResponseDto response = seatService.addSeat(seatRequestDto);

        assertEquals(seatResponseDto, response);
    }

    @Test
    void replaceSeat() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        SeatResponseDto seatResponseDto = new SeatResponseDto((long)1.2, SeatCategory.PREMIUM, 1, 2);
        SeatRequestDto seatRequestDto = new SeatRequestDto((long)1.2, (long)1.3, SeatCategory.PREMIUM, 1,2);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        when(seatRepository.save(seat)).thenReturn(seat);
        when(seatMapper.entityToDto(seat)).thenReturn(seatResponseDto);
        SeatResponseDto response = seatService.replaceSeat((long)1.2, seatRequestDto);

        assertEquals(seatResponseDto, response);
    }

    @Test
    void deleteSeat() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        seatService.deleteSeat((long)1.2);
    }

    @Test
    void saveSeat() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        when(seatRepository.existsByRowAndColumn(3, 5)).thenReturn(false);
        when(seatRepository.save(seat)).thenReturn(seat);
        Seat response = seatService.saveSeat(seat);

        assertEquals(seat, response);
    }

    @Test
    void saveSeatException(){
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        when(seatRepository.existsByRowAndColumn(3, 5)).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, ()->seatService.saveSeat(seat));
    }

    @Test
    void replaceSeats() {
        Room room = new Room( "Avengers", true, false);
        Seat seat = new Seat(room, 3, 5, SeatCategory.PREMIUM);
        SeatResponseDto seatResponseDto = new SeatResponseDto((long)1.2, SeatCategory.PREMIUM, 3, 5);
        List<Seat> seatList = List.of(seat, seat);
        List<SeatResponseDto> seatResponseDtoList = List.of(seatResponseDto, seatResponseDto);
        SeatRequestDto seatRequestDto = new SeatRequestDto((long)1.2, (long)1.3, SeatCategory.PREMIUM, 1,2);
        List<SeatRequestDto> seatRequestDtoList = List.of(seatRequestDto, seatRequestDto);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        when(seatRepository.saveAll(seatList)).thenReturn(seatList);
        when(seatMapper.entityToDto(seatList)).thenReturn(seatResponseDtoList);

        List<SeatResponseDto> response = seatService.replaceSeats(seatRequestDtoList);

        assertEquals(seatResponseDtoList, response);
    }
}