package de.cinetastisch.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

public record ReservationRequestDto(
        @Schema(nullable = true)
        @Nullable
        Long userId,
        Long screeningId,
        Long seatId
) {
}
