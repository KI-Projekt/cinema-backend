package de.cinetastisch.backend.dto;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id,
        Long screeningId,
        OrderResponseDto order,
        SeatResponseDto seat,
        LocalDateTime expiresAt
) {
}
