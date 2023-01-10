package de.cinetastisch.backend.dto;

import java.util.List;

public record SeatingRowsDto(
        String rowDescription,
        List<ScreeningSeatDto> seats
) {
}
