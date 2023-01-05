package de.cinetastisch.backend.dto;


import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.model.Ticket;

import java.util.List;
import java.util.Map;

public record OrderRequestDto(
        Long userId,
        Long screeningId,
//        List<Map<Long, TicketCategory>> seatIds
        List<Ticket> tickets
) {
}
