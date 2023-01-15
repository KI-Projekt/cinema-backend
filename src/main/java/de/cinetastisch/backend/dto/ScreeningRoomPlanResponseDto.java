package de.cinetastisch.backend.dto;

import java.util.List;

public record ScreeningRoomPlanResponseDto(
        Long screeningId,
        Long roomId,
        List<ScreeningSeatRowDto> plan
) {
}
