package de.cinetastisch.backend.service;

import de.cinetastisch.backend.enumeration.BookingStatus;
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
        Ticket newTicket = new Ticket();

        newTicket.setBooking(
                new Booking(new User("Test", "TEst", "test", "test@test.de")));
        newTicket.setSeat(new Seat(new Room(), 1, 2, "Standard"));
        newTicket.setScreening(new Screening());

        if (newTicket.getSeat().getSeatType() == "Standard"){
            newTicket.setPrice(5.0);
        } else {
            newTicket.setPrice(10.0);
        }
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
