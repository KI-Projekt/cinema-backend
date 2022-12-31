package de.cinetastisch.backend.exeption;

import java.time.LocalDateTime;

public record ErrorMessage(
        int statusCode,
        LocalDateTime date,
        String errorMessage,
        String description
) {
}
