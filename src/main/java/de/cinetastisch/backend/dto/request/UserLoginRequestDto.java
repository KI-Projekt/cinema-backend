package de.cinetastisch.backend.dto.request;

public record UserLoginRequestDto(
        String email,
        String password
) {
}
