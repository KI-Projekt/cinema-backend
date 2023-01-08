package de.cinetastisch.backend.dto;

public record ReservationRequestDto(
        Long userId,
        Long screeningId,
        Long seatId
) {
}
