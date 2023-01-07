package de.cinetastisch.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record MovieRequestDto(
        @NotBlank(message = "Movie needs a title.")
        String title,
        String releaseYear,
        String posterImage,
        String rated,
        String runtime,
        String genre,
        String director,
        String writer,
        String actors,
        String plot,
        String trailer,
        String imdbId,
        String imdbRating,
        String imdbRatingCount,
        String movieStatus
){}
