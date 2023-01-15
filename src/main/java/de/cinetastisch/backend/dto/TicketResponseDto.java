package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.TicketCategory;

public record TicketResponseDto(
        Long id,
        Long orderId,
        ScreeningResponseDto screening,
        SeatResponseDto seat,
        TicketCategory category
) {
}
