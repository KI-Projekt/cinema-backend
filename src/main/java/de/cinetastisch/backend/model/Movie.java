package de.cinetastisch.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Movie {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long movieId;
    private String imdbId;
    private String rank;
    private String title;
    private String fullTitle;
    private String releaseYear;
    private String image;
    private String crew;
    private String imdbRating;
    private String imdbRatingCount;

    public Movie() {}

    public Movie(Long movieId, String imdbId, String rank, String title, String fullTitle, String releaseYear, String image, String crew, String imdbRating, String imdbRatingCount) {
        this.movieId = movieId;
        this.imdbId = imdbId;
        this.rank = rank;
        this.title = title;
        this.fullTitle = fullTitle;
        this.releaseYear = releaseYear;
        this.image = image;
        this.crew = crew;
        this.imdbRating = imdbRating;
        this.imdbRatingCount = imdbRatingCount;
    }

    public Movie(String imdbId, String rank, String title, String fullTitle, String releaseYear, String image, String crew, String imdbRating, String imdbRatingCount) {
        this.imdbId = imdbId;
        this.rank = rank;
        this.title = title;
        this.fullTitle = fullTitle;
        this.releaseYear = releaseYear;
        this.image = image;
        this.crew = crew;
        this.imdbRating = imdbRating;
        this.imdbRatingCount = imdbRatingCount;
    }

    public Movie(String title, String releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

}
