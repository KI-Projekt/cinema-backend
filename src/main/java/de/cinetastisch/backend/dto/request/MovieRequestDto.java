package de.cinetastisch.backend.dto.request;

import de.cinetastisch.backend.enumeration.MovieRating;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MovieRequestDto(
        @NotBlank(message = "Movie needs a title.")
        String title,
        String releaseYear,
        String posterImage,
        @NotBlank(message = "Movie needs a rating.")
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
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String movieStatus
){}
