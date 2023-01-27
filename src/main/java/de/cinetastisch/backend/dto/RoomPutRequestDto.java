package de.cinetastisch.backend.dto;


import java.util.List;

public record RoomPutRequestDto(
        Long id,
        String name,
        String hasThreeD,
        String hasDolbyAtmos,
        List<SeatPutRequestDto> seats
) {
}
