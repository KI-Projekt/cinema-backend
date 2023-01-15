package de.cinetastisch.backend.dto;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id,
        Long screeningId,
        Long orderId,
        SeatResponseDto seat,
        LocalDateTime expiresAt
) {
}
