package de.cinetastisch.backend.dto;

public record RoomSlimResponseDto(
        Long id,
        String name,
        Boolean hasThreeD,
        Boolean hasDolbyAtmos
) {
}
