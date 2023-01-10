package de.cinetastisch.backend.dto;

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
        String movieStatus
){}
