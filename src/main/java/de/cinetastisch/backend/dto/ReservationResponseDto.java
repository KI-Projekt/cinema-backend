package de.cinetastisch.backend.dto;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id,
        Long screeningId,
        Integer seatRow,
        Integer seatColumn,
        LocalDateTime expiresAt
) {
}
