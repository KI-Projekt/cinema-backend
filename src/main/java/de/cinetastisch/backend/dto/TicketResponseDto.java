package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.TicketCategory;

public record TicketResponseDto(
        Long id,
        OrderResponseDto order,
        ScreeningResponseDto screening,
        SeatResponseDto seat,
        TicketCategory category
) {
}
