package de.cinetastisch.backend.service;


import de.cinetastisch.backend.dto.OrderResponseDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.mapper.UserMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.ReservationRepository;
import de.cinetastisch.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ReferenceMapper referenceMapper;

    public List<OrderResponseDto> getAllOrders(Long userId){
        if(userId != null){
            User user = referenceMapper.map(userId, User.class);
            return orderMapper.entityToDto(orderRepository.findAllByUser(user));
        }
        return orderMapper.entityToDto(orderRepository.findAll());
    }

    public OrderResponseDto getOrder(Long id){
        return orderMapper.entityToDto(orderRepository.getReferenceById(id));
    }

    public OrderResponseDto cancelOrder(Long id){
        Order orderToCancel = orderRepository.getReferenceById(id);
        orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
        return orderMapper.entityToDto(orderRepository.save(orderToCancel));
    }

    public OrderResponseDto payOrder(Long id){
        Order paidOrder = orderRepository.getReferenceById(id);
        paidOrder.setOrderStatus(OrderStatus.PAID);// Tickets sind jetzt reserviert

        // Der Ticketkauf wird in Ticket-Service abgewickelt
        // Hier findet nur die Validierung statt, dass der OrderStatus auf PAID festgelegt wird

//        List<Reservation> reservations = reservationService.getAllReservations(paidOrder);
//
//        for(Reservation r : reservations) {
//            Ticket newTicket = new Ticket(paidOrder, r.getScreening(), r.getSeat(), TicketCategory.ADULT);
//            ticketService.addTicket()
//        }

        return orderMapper.entityToDto(orderRepository.save(paidOrder));
    }
}
