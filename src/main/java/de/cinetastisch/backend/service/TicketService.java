package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.PayTicketsRequestDto;
import de.cinetastisch.backend.dto.TicketResponseDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.TicketMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.ReservationRepository;
import de.cinetastisch.backend.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;


    public List<TicketResponseDto> getAllTickets(Long orderId) {
        if(orderId != null){
            return ticketMapper.entityToDto(ticketRepository.findAllByOrder(orderRepository.getReferenceById(orderId)));
        }
        return ticketMapper.entityToDto(ticketRepository.findAll());
    }

    @Transactional
    public List<TicketResponseDto> buyTickets(PayTicketsRequestDto ticketOrder) {
        Order order = orderRepository.findById(ticketOrder.orderId())
                                     .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);

        List<Ticket> tickets = new ArrayList<>();

        // Reservations -> Tickets
        List<Reservation> reservations = reservationRepository.findAllByOrder(order);
        for(Reservation r : reservations) {
            Ticket newTicket = new Ticket(order, r.getScreening(), r.getSeat(), TicketCategory.ADULT);
            tickets.add(ticketRepository.save(newTicket));
        }

        // Clean up Reservations
        reservationRepository.deleteAllByOrder(order);
        return ticketMapper.entityToDto(tickets);
    }
}
