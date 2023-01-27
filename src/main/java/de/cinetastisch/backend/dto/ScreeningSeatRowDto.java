package de.cinetastisch.backend.dto;

import java.util.List;

public record ScreeningSeatRowDto(
        String rowDescription,
        List<ScreeningSeatDto> row
) {
}
