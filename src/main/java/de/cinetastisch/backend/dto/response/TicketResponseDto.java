package de.cinetastisch.backend.dto.response;

import de.cinetastisch.backend.enumeration.TicketType;

import java.time.LocalDateTime;


public record TicketResponseDto(
        Long id,
        Long orderId,
        ScreeningResponseDto screening,
        SeatResponseDto seat,
        String category,
        TicketType type,
        LocalDateTime createdAt
) {
}
