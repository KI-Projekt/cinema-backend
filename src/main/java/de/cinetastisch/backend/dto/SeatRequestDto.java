package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.SeatCategory;

public record SeatRequestDto(Long roomId,
                             SeatCategory category,
                             Integer row,
                             Integer column) {
}
