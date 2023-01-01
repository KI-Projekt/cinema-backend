package de.cinetastisch.backend.exception;

import java.time.LocalDateTime;

public record ErrorMessage(
        int statusCode,
        LocalDateTime date,
        String errorMessage,
        String description
) {
}
