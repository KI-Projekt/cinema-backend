package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ScreeningResponseDto(Long id,
                                   ScreeningStatus status,
                                   Movie movie,
                                   Room room,
                                   LocalDateTime startDateTime,
                                   LocalDateTime endDateTime) {
}
