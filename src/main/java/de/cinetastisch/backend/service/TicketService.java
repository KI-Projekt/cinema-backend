package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public boolean checkIfSeatIsAlreadyBooked(Screening screening, Seat seat){
        return ticketRepository.existsByScreeningAndSeat(screening, seat);
    }

    public Ticket saveTicket(Order order, Screening screening, Seat seat) {
        if(ticketRepository.existsByScreeningAndSeat(screening, seat)){
            throw new ResourceAlreadyOccupiedException("Ticket is already reserved");
        }
        Ticket newTicket = new Ticket(order, screening, seat);
        return ticketRepository.save(newTicket);
    }
}
