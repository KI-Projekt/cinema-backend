package de.cinetastisch.backend.dto;


import java.time.LocalDateTime;

public record OrderRequestDto(
        Long userId,
        Long screeningId,
        LocalDateTime created
) {
}
