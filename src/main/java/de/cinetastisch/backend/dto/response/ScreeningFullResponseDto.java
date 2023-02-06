package de.cinetastisch.backend.dto.response;

import de.cinetastisch.backend.enumeration.ScreeningStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ScreeningFullResponseDto(
        Long id,
        ScreeningStatus status,
        MovieSlimResponseDto movie,
        RoomSlimResponseDto room,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Long duration,
        boolean isThreeD,
        boolean isDolbyAtmos,
        List<ScreeningSeatRowDto> seatingPlan
) {
}
