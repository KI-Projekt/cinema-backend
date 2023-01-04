package de.cinetastisch.backend.dto;

import java.time.LocalDateTime;

public record ScreeningRequestDto(Long movieId,
                                  Long roomId,
                                  LocalDateTime startTime,
                                  LocalDateTime endTime) {} // TODO: Optional