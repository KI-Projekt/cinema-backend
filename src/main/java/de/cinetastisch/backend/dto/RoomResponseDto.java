package de.cinetastisch.backend.dto;

public record RoomResponseDto(
        Long id,
        String name,
        String hasThreeD,
        String hasDolbyAtmos
) {
}
