package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.TicketCategory;

public record TicketRequestDto(
        Long seatId,
        TicketCategory category
) {
}
