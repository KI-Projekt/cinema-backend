package de.cinetastisch.backend.dto;

import java.util.List;

public record SeatRowDto(
        String rowDescription,
        List<SeatResponseDto> seats
) {
}
