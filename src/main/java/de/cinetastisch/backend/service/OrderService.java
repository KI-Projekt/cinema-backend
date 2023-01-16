package de.cinetastisch.backend.service;


import de.cinetastisch.backend.dto.FaresDto;
import de.cinetastisch.backend.dto.OrderResponseDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ReferenceMapper referenceMapper;
    private final ReservationRepository reservationRepository;

    public List<OrderResponseDto> getAllOrders(Long userId){
        if(userId != null){
            User user = referenceMapper.map(userId, User.class);
            return orderMapper.entityToDto(orderRepository.findAllByUser(user));
        }
        return orderMapper.entityToDto(orderRepository.findAll());
    }

    public OrderResponseDto getOrder(Long id){
        Order order = orderRepository.getReferenceById(id);
        OrderResponseDto orderDto = orderMapper.entityToDto(order);
        return orderDto;
    }

    public OrderResponseDto cancelOrder(Long id){
        Order orderToCancel = orderRepository.getReferenceById(id);
        orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
        reservationRepository.deleteAllByOrder(orderToCancel);
        orderToCancel.setReservations(new ArrayList<>());
        return orderMapper.entityToDto(orderRepository.save(orderToCancel));
    }

    public OrderResponseDto payOrder(Long id){
        Order paidOrder = orderRepository.getReferenceById(id);
        paidOrder.setOrderStatus(OrderStatus.PAID);
        List<Ticket> ticketList = new ArrayList<>();
        // Es soll doch hier der Kauf abgewickelt werden.

        // 1. Reservations sollen zu Tickets umgewandelt werden

        for(Reservation r : paidOrder.getReservations() ) {
            Ticket newTicket = new Ticket(paidOrder, r.getScreening(), r.getSeat(), r.getCategory());
            ticketList.add(newTicket);
        }
        paidOrder.setTickets(ticketList);
//        paidOrder.setReservations(new ArrayList<>());
        reservationRepository.deleteAllByOrder(paidOrder);
        paidOrder.setReservations(new ArrayList<>());
        orderRepository.save(paidOrder);
        return orderMapper.entityToDto(paidOrder);
    }

    @Transactional
    public OrderResponseDto selectFares(Long id, FaresDto fares) {
        Order order = orderRepository.getReferenceById(id);
        int kids = fares.kidsCount();
        int students = fares.studentCounts();
        int adults = fares.adultsCount();
        int pensioners = fares.pensionerCount();

        if(order.getReservations().size() != (kids + students + adults + pensioners)){
            throw new IllegalArgumentException("Number of fares does not match with the number of reservations");
        }

        for(Reservation r : order.getReservations()){
            if(kids != 0){
                r.setCategory(TicketCategory.KID);
                kids--;
            } else if ( students != 0){
                r.setCategory(TicketCategory.STUDENT);
                students--;
            } else if ( adults != 0){
                r.setCategory(TicketCategory.ADULT);
                adults--;
            } else if ( pensioners != 0){
                r.setCategory(TicketCategory.PENSIONER);
                pensioners--;
            }

        }
        orderRepository.save(order);
        return orderMapper.entityToDto(order);
    }
}
