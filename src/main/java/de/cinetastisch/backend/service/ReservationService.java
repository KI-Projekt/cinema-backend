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
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final TicketMapper ticketMapper;
    private final OrderMapper orderMapper;
    private final ReferenceMapper referenceMapper;
    private final TicketFareRepository ticketFareRepository;

    public List<TicketResponseDto> getAllReservations(Long userId, Long screeningId){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        if(screeningId != null){
            return ticketMapper.entityToDto(ticketRepository.findAllByScreening(referenceMapper.map(screeningId, Screening.class)));
        }
        return ticketMapper.entityToDto(ticketRepository.findAll());
    }

    @Transactional
    public OrderResponseDto addReservation(ReservationRequestDto reservation, HttpServletRequest request){

        if(request.getUserPrincipal() != null){
            System.out.println(request.getUserPrincipal().getName());
        }

        System.out.println("req " + request.getRequestedSessionId() + " " + request.getSession().getId() + " " + request.getSession().getAttribute("JSESSIONID"));


        Seat seat = seatRepository.getReferenceById(reservation.seatId());
        Screening screening = screeningRepository.getReferenceById(reservation.screeningId());
        Order order;
        Ticket ticket;

        if(screening.getStatus() != ScreeningStatus.TICKET_SALE_OPEN){
            throw new ResourceAlreadyOccupiedException("Can't Book Screenings which are cancelled or have already ended");
        }

        if(seat.getRoom() != screening.getRoom()){
            throw new IllegalArgumentException("Seat ID not in Screening");
        }

        if(ticketRepository.existsByScreeningAndSeat(screening, seat)){
            throw new ResourceAlreadyOccupiedException("Seat is already reserved");
        }
        if(request.getUserPrincipal() != null || reservation.userId() != null){
            System.out.println("USER CREATION");
            User user;

            if(reservation.userId() != null) {
                user = userRepository.getReferenceById(reservation.userId());
            } else {
                user = userRepository.getByEmail(request.getUserPrincipal().getName());
            }

            if(orderRepository.existsByUserAndStatusAndTicketsScreening(user, OrderStatus.IN_PROGRESS, screening)){
                order = orderRepository.findByUserAndStatusAndTicketsScreening(user, OrderStatus.IN_PROGRESS, screening);
            } else {
                order = new Order(user);
                orderRepository.save(order);
            }
        } else {
            System.out.println("SESSION CREATION");
            if(orderRepository.existsBySessionAndStatusAndTicketsScreening(request.getRequestedSessionId(), OrderStatus.IN_PROGRESS, screening)){
                order = orderRepository.findBySessionAndStatusAndTicketsScreening(request.getRequestedSessionId(), OrderStatus.IN_PROGRESS, screening);
            } else {
                order = new Order(request.getRequestedSessionId());
                orderRepository.save(order);
            }
        }


        ticket = new Ticket(order, screening, seat, ticketFareRepository.findByNameLikeIgnoreCase("Adult"));
        order.getTickets().add(ticket);
        ticketRepository.save(ticket);
        orderRepository.save(order);
        return orderMapper.entityToDto(referenceMapper.map(order.getId(), Order.class));
    }

    @Transactional
    @Transient
    public void deleteReservation(Long id){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());

        if(ticketRepository.getReferenceById(id).getType() == TicketType.TICKET){
            throw new ResourceAlreadyOccupiedException("Ticket has already been paid");
        }

        ticketRepository.deleteById(id);
    }
}
