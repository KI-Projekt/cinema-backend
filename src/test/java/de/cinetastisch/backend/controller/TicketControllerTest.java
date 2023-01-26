package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.*;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class TicketControllerTest {
    @InjectMocks
    TicketController ticketController;
    @Mock
    TicketService ticketService;


    @Test
    void getTickets() {
        LocalDateTime localDateTime = LocalDateTime.of(2022,12,12,12,12,12);
        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
        SeatResponseDto seatResponseDto =new SeatResponseDto((long)1.22, SeatCategory.PREMIUM,2,2);
        RoomResponseDto roomResponseDto = new RoomResponseDto((long)1.2,"Test","true","true");
        ScreeningResponseDto screeningResponseDto = new ScreeningResponseDto((long)1.2, ScreeningStatus.TICKET_SALE_OPEN,(long)1.2,roomResponseDto,localDateTime,localDateTime,(long)1.2);
        TicketResponseDto responseDto = new TicketResponseDto((long)1.2,orderResponseDto,screeningResponseDto,seatResponseDto, TicketCategory.STUDENT);
        List<TicketResponseDto> responseDtoList = List.of(responseDto);

        when(ticketService.getAllTickets()).thenReturn(responseDtoList);

        List<TicketResponseDto> response = ticketController.getTickets();

        assertEquals(responseDtoList,response);


    }

    @Test
    void buyTickets() {
        LocalDateTime localDateTime = LocalDateTime.of(2022,12,12,12,12,12);
        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
        SeatResponseDto seatResponseDto =new SeatResponseDto((long)1.22, SeatCategory.PREMIUM,2,2);
        RoomResponseDto roomResponseDto = new RoomResponseDto((long)1.2,"Test","true","true");
        ScreeningResponseDto screeningResponseDto = new ScreeningResponseDto((long)1.2, ScreeningStatus.TICKET_SALE_OPEN,(long)1.2,roomResponseDto,localDateTime,localDateTime,(long)1.2);
        TicketResponseDto responseDto = new TicketResponseDto((long)1.2,orderResponseDto,screeningResponseDto,seatResponseDto, TicketCategory.STUDENT);
        List<TicketResponseDto> responseDtoList = List.of(responseDto);
        List<Long> fares = List.of((long)1.2,(long)1.2);
        PayTicketsRequestDto payTicketsRequestDto = new PayTicketsRequestDto((long)1.2,fares);
        when(ticketService.buyTickets(payTicketsRequestDto)).thenReturn(responseDtoList);

        List<TicketResponseDto> response = ticketController.buyTickets(payTicketsRequestDto);

        assertEquals(responseDtoList,response);
    }
}