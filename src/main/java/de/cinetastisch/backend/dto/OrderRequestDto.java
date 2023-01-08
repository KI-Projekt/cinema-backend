package de.cinetastisch.backend.dto;

import java.util.List;

public record OrderRequestDto(
        Long userId,
        Long screeningId,
//        List<Map<Long, TicketCategory>> seatIds
//        List<Ticket> tickets
//        List<Long> seatIds
        List<TicketRequestDto> tickets
) {
}
