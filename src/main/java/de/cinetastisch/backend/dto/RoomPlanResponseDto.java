package de.cinetastisch.backend.dto;

import java.util.List;

public record RoomPlanResponseDto(
        Long screeningId,
        Long roomId,
        List<ScreeningSeatingRowsDto> plan
) {
}
