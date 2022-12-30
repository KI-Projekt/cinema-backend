package de.cinetastisch.backend.model.info;

import java.time.LocalDateTime;

public record ScreeningInfo(Long movieId,
                            String movieTitle,
                            Long roomId,
                            String roomName,
                            LocalDateTime startTime,
                            LocalDateTime endTime) {} // record hat keine Setter-Methoden.
