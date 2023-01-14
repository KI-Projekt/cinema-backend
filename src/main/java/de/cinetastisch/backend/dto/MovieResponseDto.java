package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.MovieStatus;

public record MovieResponseDto(
        Long id,
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
        MovieStatus movieStatus
){}
