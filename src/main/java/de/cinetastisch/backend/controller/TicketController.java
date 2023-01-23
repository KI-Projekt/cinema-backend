package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.TicketResponseDto;
import de.cinetastisch.backend.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Operation(
            tags = {"Tickets"}
    )
    @GetMapping
    public List<TicketResponseDto> getTickets(@RequestParam(value = "orderId", required = false) Long orderId){
        return ticketService.getAllTickets(orderId);
    }

}
