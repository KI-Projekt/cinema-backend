package de.cinetastisch.backend.dto.response;

public record MovieSlimResponseDto(
        Long id,
        String title,
        String releaseYear,
        String posterImage,
        String rated,
        String genre

) {
}
