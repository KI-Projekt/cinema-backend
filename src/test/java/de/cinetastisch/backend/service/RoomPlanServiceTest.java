package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningSeatDto;
import de.cinetastisch.backend.dto.response.ScreeningSeatRowDto;
import de.cinetastisch.backend.dto.response.SeatResponseDto;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ListResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RoomPlanServiceTest {
    @InjectMocks
    RoomPlanService roomPlanService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    TicketRepository ticketRepository;

    @Test
    void getSeatingPlan() {
        SeatResponseDto seatResponseDto = new SeatResponseDto(null, SeatCategory.NORMAL, 1, 1);
        ScreeningSeatDto screeningSeatDto = new ScreeningSeatDto(seatResponseDto, false);
        List<ScreeningSeatDto> screeningSeatDtoList = List.of(screeningSeatDto);
        ScreeningSeatRowDto screeningSeatRowDto = new ScreeningSeatRowDto("Reihe1", screeningSeatDtoList);
        List<ScreeningSeatRowDto> screeningSeatRowDtos = List.of(screeningSeatRowDto);
        Movie movie = new Movie();
        Room room = new Room("Star Wars", true, true);
        Seat seat = new Seat(room,1,1,SeatCategory.NORMAL);
        List<Seat> seatList = List.of(seat);
        room.setSeats(seatList);
        Screening screening = new Screening(movie, room, null, null, true, true, ScreeningStatus.TICKET_SALE_OPEN);


        List<ScreeningSeatRowDto> respone = roomPlanService.getSeatingPlan(screening);

        assertEquals(screeningSeatRowDtos,respone);
    }
}