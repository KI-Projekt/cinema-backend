package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record RoomPlanResponseDto(
        Long screeningId,
        Long roomId,
        List<SeatingRowsDto> plan
) {
}
