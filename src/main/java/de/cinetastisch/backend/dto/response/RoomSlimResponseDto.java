package de.cinetastisch.backend.dto.response;

public record RoomSlimResponseDto(
        Long id,
        String name,
        Boolean hasThreeD,
        Boolean hasDolbyAtmos
) {
}
