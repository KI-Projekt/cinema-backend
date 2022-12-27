package de.cinetastisch.backend.service;

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

    public void addTicket(Ticket ticket){
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
