package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.TicketCategory;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id,
        Long screeningId,
        Long orderId,
        SeatResponseDto seat,
        TicketCategory category,
        LocalDateTime expiresAt
) {
}
