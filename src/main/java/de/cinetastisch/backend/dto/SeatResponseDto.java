package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.SeatCategory;

public record SeatResponseDto(
        Long id,
        SeatCategory category,
        Integer row,
        Integer column
) {}
