package de.cinetastisch.backend.dto;

import java.util.List;

public record SeatRowsDto(
        String rowDescription,
        List<SeatResponseDto> seats
) {
}
