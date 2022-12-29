package de.cinetastisch.backend.model.info;

import java.time.LocalDateTime;

public record ScreeningInfo(Long movieId,
                            Long roomId,
                            LocalDateTime startTime,
                            LocalDateTime endTime) {} // record hat keine Setter-Methoden.
