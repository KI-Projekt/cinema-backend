package de.cinetastisch.backend.dto;

public record MovieSlimResponseDto(
        Long id,
        String title,
        String releaseYear,
        String posterImage,
        String rated,
        String genre

) {
}
