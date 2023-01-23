package de.cinetastisch.backend.service;


import de.cinetastisch.backend.dto.FaresDto;
import de.cinetastisch.backend.dto.OrderResponseDto;
import de.cinetastisch.backend.dto.ReservationRequestDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.enumeration.TicketType;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ReferenceMapper referenceMapper;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final TicketRepository ticketRepository;


    public List<OrderResponseDto> getAllOrders(Long userId){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        if(userId != null){
            User user = referenceMapper.map(userId, User.class);
            return orderMapper.entityToDto(orderRepository.findAllByUser(user));
        }
        return orderMapper.entityToDto(orderRepository.findAll());
    }

    public OrderResponseDto getOrder(Long id){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        Order order = orderRepository.getReferenceById(id);
        return orderMapper.entityToDto(order);
    }

//    @Transactional
//    public OrderResponseDto cancelOrder(Long id){
//        Order orderToCancel = orderRepository.getReferenceById(id);
//        orderToCancel.setStatus(OrderStatus.CANCELLED);
//        orderToCancel.setReservations(new ArrayList<>());
//        return orderMapper.entityToDto(orderRepository.save(orderToCancel));
//    }

//    @Transactional
//    public OrderResponseDto payOrder(Long id){
//        Order paidOrder = orderRepository.getReferenceById(id);
//
//        if(paidOrder.getStatus() != OrderStatus.IN_PROGRESS){
//            throw new IllegalArgumentException("Order cannot be paid because it's " + paidOrder.getStatus());
//        }
//
////        List<Reservation> reservations = new ArrayList<>(paidOrder.getReservations());
//        List<Ticket> ticketList = new ArrayList<>();
//
//
//
//        // Es soll doch hier der Kauf abgewickelt werden.
//        // 1. Reservations sollen zu Tickets umgewandelt werden
//
//        for(Reservation r : paidOrder.getReservations() ) {
//            Ticket newTicket = new Ticket(paidOrder, r.getScreening(), r.getSeat(), r.getCategory());
//            ticketList.add(newTicket);
//        }
//        paidOrder.setTickets(ticketList);
//        paidOrder.setReservations(new ArrayList<>());
//        paidOrder.setStatus(OrderStatus.PAID);
//        orderRepository.save(paidOrder);
//        return orderMapper.entityToDto(paidOrder);
//    }

//    @Transactional
//    public OrderResponseDto selectFares(Long id, FaresDto fares) {
//        Order order = orderRepository.getReferenceById(id);
//        int kids = fares.kidsCount();
//        int students = fares.studentCounts();
//        int adults = fares.adultsCount();
//        int pensioners = fares.pensionerCount();
//
//        if(order.getReservations().size() != (kids + students + adults + pensioners)){
//            throw new IllegalArgumentException("Number of fares does not match with the number of reservations");
//        }
//
//        for(Reservation r : order.getReservations()){
//            if(kids != 0){
//                r.setCategory(TicketCategory.KID);
//                kids--;
//            } else if ( students != 0){
//                r.setCategory(TicketCategory.STUDENT);
//                students--;
//            } else if ( adults != 0){
//                r.setCategory(TicketCategory.ADULT);
//                adults--;
//            } else if ( pensioners != 0){
//                r.setCategory(TicketCategory.PENSIONER);
//                pensioners--;
//            }
//
//        }
//        orderRepository.save(order);
//        return orderMapper.entityToDto(order);
//    }

    @Transactional
    public OrderResponseDto placeTicketOrder(ReservationRequestDto reservation){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        User user = userRepository.getReferenceById(reservation.userId());
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

        order = orderRepository.existsByUserAndStatus(user, OrderStatus.IN_PROGRESS)
                ? orderRepository.findAllByUserAndStatus(user, OrderStatus.IN_PROGRESS).get(0) : new Order(user);

        ticket = new Ticket(order, screening, seat);
        order.getTickets().add(ticket);

        orderRepository.save(order);
        return orderMapper.entityToDto(order);
    }

    @Transactional
    public OrderResponseDto removeTicketOrder(Long orderId, Long ticketId){
        ticketRepository.deleteById(ticketId);
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());

        return orderMapper.entityToDto(orderRepository.getReferenceById(orderId));
    }

    @Transactional
    public OrderResponseDto selectFares(Long id, FaresDto fares) {
        Order order = orderRepository.getReferenceById(id);

        int kids = fares.kidsCount();
        int students = fares.studentCounts();
        int adults = fares.adultsCount();
        int pensioners = fares.pensionerCount();

        ticketRepository.deleteAllByOrderStatus(OrderStatus.CANCELLED);
        if(order.getStatus() != OrderStatus.IN_PROGRESS){
            throw new ResourceAlreadyExistsException("Order is already " + order.getStatus());
        }

        if(order.getTickets().size() != (kids + students + adults + pensioners)){
            throw new IllegalArgumentException("Number of fares does not match with the number of reservations");
        }

        for(Ticket t : order.getTickets()){
            if(kids != 0){
                t.setCategory(TicketCategory.KID);
                kids--;
            } else if ( students != 0){
                t.setCategory(TicketCategory.STUDENT);
                students--;
            } else if ( adults != 0){
                t.setCategory(TicketCategory.ADULT);
                adults--;
            } else if ( pensioners != 0){
                t.setCategory(TicketCategory.PENSIONER);
                pensioners--;
            }

        }
        orderRepository.save(order);
        return orderMapper.entityToDto(order);
    }

    @Transactional
    public OrderResponseDto payOrder(Long id){
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        Order paidOrder = orderRepository.getReferenceById(id);

        if(paidOrder.getStatus() != OrderStatus.IN_PROGRESS){
            throw new IllegalArgumentException("Order cannot be paid because it's " + paidOrder.getStatus());
        }

        for(Ticket t : paidOrder.getTickets()) {
            t.setType(TicketType.TICKET);
        }

        paidOrder.setStatus(OrderStatus.PAID);
        orderRepository.save(paidOrder);
        return orderMapper.entityToDto(paidOrder);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long id){
        Order orderToCancel = orderRepository.getReferenceById(id);

        if(orderToCancel.getStatus() != OrderStatus.IN_PROGRESS){
            throw new ResourceAlreadyExistsException("Order is already " + orderToCancel.getStatus());
        }

        orderToCancel.setStatus(OrderStatus.CANCELLED);
        ticketRepository.deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus.CANCELLED, LocalDateTime.now());
        orderRepository.save(orderToCancel);
        return orderMapper.entityToDto(orderToCancel);
    }

}
