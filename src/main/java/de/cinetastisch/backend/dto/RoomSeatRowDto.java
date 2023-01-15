package de.cinetastisch.backend.dto;

import java.util.List;

public record RoomSeatRowDto(
        String rowDescription,
        List<SeatResponseDto> seats
) {
}
