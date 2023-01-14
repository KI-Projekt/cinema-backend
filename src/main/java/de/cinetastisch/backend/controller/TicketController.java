package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.PayTicketsRequestDto;
import de.cinetastisch.backend.dto.TicketResponseDto;
import de.cinetastisch.backend.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(
            tags = {"Tickets"}
    )
    @GetMapping
    public List<TicketResponseDto> getTickets(@RequestParam("orderId") Long orderId){
        return ticketService.getAllTickets(orderId);
    }

    @Operation(
            tags = {"Tickets"}
    )
    @PostMapping()
    public List<TicketResponseDto> buyTickets(PayTicketsRequestDto order){
        return ticketService.buyTickets(order);
    }

}
