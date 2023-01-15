package de.cinetastisch.backend.dto;

import java.util.List;

public record RoomResponseDto(
        Long id,
        String name,
        Boolean hasThreeD,
        Boolean hasDolbyAtmos,
        List<RoomSeatRowDto> rows
) {
}
