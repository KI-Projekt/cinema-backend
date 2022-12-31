package de.cinetastisch.backend.pojo;

public record MovieRequest(String title,
                           String releaseYear,
                           String posterImage,
                           String rated,
                           String runtime,
                           String genre,
                           String actors,
                           String plot,
                           String trailer,
                           String imdbId,
                           String imdbRating,
                           String imdbRatingCount) {
}
