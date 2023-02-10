package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.ReservationRequestDto;
import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.dto.response.TicketResponseDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.enumeration.TicketType;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.mapper.TicketMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.*;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    ReservationService reservationService;
    @Mock
    SeatRepository seatRepository;
    @Mock
    ScreeningRepository screeningRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    TicketFareRepository ticketFareRepository;
    @Mock
    OrderMapper orderMapper;
    @Mock
    ReferenceMapper referenceMapper;
    @Mock
    UserRepository userRepository;

    @Mock
    TicketMapper ticketMapper;


    @Test
    void getAllReservations() {
        Order order = new Order("1");
        Screening screening = new Screening();
        Ticket ticket = new Ticket(null, null, null, null, null);
        List<Ticket> ticketList = List.of(ticket);
        TicketResponseDto ticketResponseDto = new TicketResponseDto(null, null, null, null, null, null, null);
        List<TicketResponseDto> ticketResponseDtoList = List.of(ticketResponseDto);
        order.setStatus(OrderStatus.CANCELLED);
        LocalDateTime localDateTime = LocalDateTime.of(12, 12, 12, 12, 12, 12);
        when(ticketRepository.findAll()).thenReturn(ticketList);
        when(ticketMapper.entityToDto(ticketList)).thenReturn(ticketResponseDtoList);
        when(ticketRepository.findAllByScreening(screening)).thenReturn(ticketList);
        when(referenceMapper.map(null, Screening.class)).thenReturn(screening);
        List<TicketResponseDto> response = reservationService.getAllReservations((long)1.2, null);
        assertEquals(ticketResponseDtoList, response);

    }

    @Test
    void addReservationAlreadyOccupiedStatus() {
        OrderResponseDto orderResponseDto = new OrderResponseDto(null,null,null,null,null,null,null,null,null,null);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(null,(long)1.2,(long)1.2);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Screening screening = new Screening(null,null,null,null,null,true,true,null);
        Seat seat = new Seat(null,null,null,null);
        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);

        assertThrows(ResourceAlreadyOccupiedException.class,()->reservationService.addReservation(reservationRequestDto,httpServletRequest));
    }
    @Test
    void addReservationIllegalArgument() {
        OrderResponseDto orderResponseDto = new OrderResponseDto(null,null,null,null,null,null,null,null,null,null);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(null,(long)1.2,(long)1.2);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Room room = new Room(null,true,true);
        Screening screening = new Screening(null,null,room,null,null,true,true, ScreeningStatus.TICKET_SALE_OPEN);
        Seat seat = new Seat(null,null,null,null);
        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);

        assertThrows(IllegalArgumentException.class,()->reservationService.addReservation(reservationRequestDto,httpServletRequest));
    }
    @Test
    void addReservationAlreadyOccupiedexists() {
        OrderResponseDto orderResponseDto = new OrderResponseDto(null,null,null,null,null,null,null,null,null,null);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(null,(long)1.2,(long)1.2);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Room room = new Room(null,true,true);
        Screening screening = new Screening(null,null,null,null,null,true,true, ScreeningStatus.TICKET_SALE_OPEN);
        Seat seat = new Seat(null,null,null,null);
        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        when(ticketRepository.existsByScreeningAndSeat(screening,seat)).thenReturn(true);

        assertThrows(ResourceAlreadyOccupiedException.class,()->reservationService.addReservation(reservationRequestDto,httpServletRequest));
    }
    @Test
    void addReservationelse() {
        OrderResponseDto orderResponseDto = new OrderResponseDto(null,null,null,null,null,null,null,null,null,null);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(null,(long)1.2,(long)1.2);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Order order = new Order("1");
        order.setId((long)1.2);
        Room room = new Room(null,true,true);
        Screening screening = new Screening(null,null,null,null,null,true,true, ScreeningStatus.TICKET_SALE_OPEN);
        Seat seat = new Seat(null,null,null,null);
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        TicketFare ticketFare = new TicketFare("Adult",20.0,"cool");
        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        when(ticketRepository.existsByScreeningAndSeat(screening,seat)).thenReturn(false);

        httpServletRequest.setUserPrincipal(null);
        when(orderRepository.existsBySessionAndStatusAndTicketsScreening("1", OrderStatus.IN_PROGRESS,screening)).thenReturn(true);
        when(orderRepository.findBySessionAndStatusAndTicketsScreening(httpServletRequest.getSession().getId(),OrderStatus.IN_PROGRESS,screening)).thenReturn(order);
        when(ticketFareRepository.findByNameLikeIgnoreCase("Adult")).thenReturn(ticketFare);
        when(referenceMapper.map((long)1.2,Order.class)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);

        OrderResponseDto response = reservationService.addReservation(reservationRequestDto,httpServletRequest);
        assertEquals(orderResponseDto,response);
    }

    @Test
    void addReservationif() {
        OrderResponseDto orderResponseDto = new OrderResponseDto(null,null,null,null,null,null,null,null,null,null);
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto((long)1.2,(long)1.2,(long)1.2);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Order order = new Order("1");
        Room room = new Room(null,true,true);
        Screening screening = new Screening(null,null,null,null,null,true,true, ScreeningStatus.TICKET_SALE_OPEN);
        Seat seat = new Seat(null,null,null,null);
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        TicketFare ticketFare = new TicketFare("Adult",20.0,"cool");

        when(screeningRepository.getReferenceById((long)1.2)).thenReturn(screening);
        when(seatRepository.getReferenceById((long)1.2)).thenReturn(seat);
        when(ticketRepository.existsByScreeningAndSeat(screening,seat)).thenReturn(false);

        when(userRepository.getReferenceById((long)1.2)).thenReturn(user);
        when(orderRepository.existsByUserAndStatusAndTicketsScreening(user,OrderStatus.IN_PROGRESS,screening)).thenReturn(true);
        when(orderRepository.findByUserAndStatusAndTicketsScreening(user,OrderStatus.IN_PROGRESS,screening)).thenReturn(order);
        when(ticketFareRepository.findByNameLikeIgnoreCase("Adult")).thenReturn(ticketFare);
        when(referenceMapper.map(null,Order.class)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);

        OrderResponseDto response = reservationService.addReservation(reservationRequestDto,httpServletRequest);

        assertEquals(orderResponseDto,response);


        }


    @Test
    void deleteReservation() {
        Ticket ticket = new Ticket(null, null, null, null, TicketType.TICKET);
        when(ticketRepository.getReferenceById((long)1.2)).thenReturn(ticket);
        assertThrows(ResourceAlreadyOccupiedException.class, ()->reservationService.deleteReservation((long)1.2));

    }
}
