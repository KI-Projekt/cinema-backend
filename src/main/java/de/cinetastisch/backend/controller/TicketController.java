package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> getTickets(){
        return ticketService.getAllTickets();
    }

    @PostMapping
    public void addTicket(@RequestBody Ticket ticket){
        ticketService.addTicket(ticket);
    }
}
