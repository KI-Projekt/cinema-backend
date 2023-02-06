package de.cinetastisch.backend.dto.response;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String role,
        String email
) {
}
