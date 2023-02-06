package de.cinetastisch.backend.dto.response;

import java.util.List;

public record RoomSeatRowDto(
        String rowDescription,
        List<SeatResponseDto> seats
) {
}
