package de.cinetastisch.backend.dto.response;

import de.cinetastisch.backend.enumeration.ScreeningStatus;

import java.time.LocalDateTime;

public record ScreeningResponseDto(
        Long id,
        ScreeningStatus status,
        MovieSlimResponseDto movie,
        RoomSlimResponseDto room,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        boolean isThreeD,
        boolean isDolbyAtmos,
        Long duration) {
}
