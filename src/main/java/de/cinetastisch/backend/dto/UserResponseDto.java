package de.cinetastisch.backend.dto;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
