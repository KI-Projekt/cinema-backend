package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.SeatCategory;
import io.swagger.v3.oas.annotations.media.Schema;

public record SeatDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        Long roomId,
        SeatCategory category,
        Integer row,
        Integer column) {
}
