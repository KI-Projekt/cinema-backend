package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.*;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketType;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.mapper.TicketMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.*;
import jakarta.servlet.http.HttpSession;
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

    public List<TicketResponseDto> getAllReservations(Long userId, Long screeningId){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        if(screeningId != null){
            return ticketMapper.entityToDto(ticketRepository.findAllByScreening(referenceMapper.map(screeningId, Screening.class)));
        }
        return ticketMapper.entityToDto(ticketRepository.findAll());
    }

    @Transactional
    public OrderResponseDto addReservation(ReservationRequestDto reservation, HttpSession session){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        System.out.println(session.getId());


        Seat seat = seatRepository.getReferenceById(reservation.seatId());
        Screening screening = screeningRepository.getReferenceById(reservation.screeningId());
        Order order;
        Ticket ticket;

        if(seat.getRoom() != screening.getRoom()){
            throw new IllegalArgumentException("Seat ID not in Screening");
        }
        if(ticketRepository.existsByScreeningAndSeat(screening, seat)){
            throw new ResourceAlreadyOccupiedException("Seat is already reserved");
        }

////        List<Order> existingOrders = orderRepository.findAllByUserAndStatus(user, OrderStatus.IN_PROGRESS);
//        List<Order> existingOrders = orderRepository.findAllBySession(session.getId());
//        if(existingOrders.size() > 0 && LocalDateTime.now().isBefore(existingOrders.get(0).getExpiresAt())){
////            order =  orderRepository.findAllByUserAndStatus(user, OrderStatus.IN_PROGRESS).get(0);
//            order =  existingOrders.get(0);
//        } else {
//            order = new Order(session.getId());
//        }
        if(reservation.userId() != null){
            User user = userRepository.getReferenceById(reservation.userId());
            if(orderRepository.existsByUserAndStatus(user, OrderStatus.IN_PROGRESS)){
                order = orderRepository.findByUserAndStatus(user, OrderStatus.IN_PROGRESS);
            } else {
                order = new Order(user);
            }
        } else {
            order = orderRepository.existsBySessionAndStatus(session.getId(), OrderStatus.IN_PROGRESS) ? orderRepository.findBySessionAndStatus(
                    session.getId(), OrderStatus.IN_PROGRESS) : new Order(session.getId());
        }

        ticket = new Ticket(order, screening, seat);
        order.getTickets().add(ticket);
        ticketRepository.save(ticket);
        return orderMapper.entityToDto(orderRepository.getReferenceById(order.getId()));
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
