package de.cinetastisch.backend.dto.response;

import de.cinetastisch.backend.enumeration.SeatCategory;

public record SeatResponseDto(
        Long id,
        SeatCategory category,
        Integer row,
        Integer column
) {}
