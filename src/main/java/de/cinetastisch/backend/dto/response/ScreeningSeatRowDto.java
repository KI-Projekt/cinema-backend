package de.cinetastisch.backend.dto.response;

import java.util.List;

public record ScreeningSeatRowDto(
        String rowDescription,
        List<ScreeningSeatDto> row
) {
}
