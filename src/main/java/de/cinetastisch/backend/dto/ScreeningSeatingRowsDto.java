package de.cinetastisch.backend.dto;

import java.util.List;

public record ScreeningSeatingRowsDto(
        String rowDescription,
        List<ScreeningSeatDto> seats
) {
}
