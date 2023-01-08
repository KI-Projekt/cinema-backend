package de.cinetastisch.backend.service;


import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
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
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public List<Order> getAllOrders(Long userId){
        if(userId != null){
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id Not found"));
            return orderRepository.findAllByUser(user);
        }
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order ID not found"));
    }

    public Order cancelOrder(Long id){
        Order orderToCancel = getOrder(id);
        orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(orderToCancel);
    }

    public Order cancelOrder(Order order){
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public Order payOrder(Long id){
        Order paidOrder = getOrder(id);
        paidOrder.setOrderStatus(OrderStatus.PAID);// Tickets sind jetzt reserviert

        // Der Ticketkauf wird in Ticket-Service abgewickelt
        // Hier findet nur die Validierung statt, dass der OrderStatus auf PAID festgelegt wird

//        List<Reservation> reservations = reservationService.getAllReservations(paidOrder);
//
//        for(Reservation r : reservations) {
//            Ticket newTicket = new Ticket(paidOrder, r.getScreening(), r.getSeat(), TicketCategory.ADULT);
//            ticketService.addTicket()
//        }

        return orderRepository.save(paidOrder);
    }
}
