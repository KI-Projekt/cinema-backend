package de.cinetastisch.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie { //Title, Rated, Runtime, Genre, Director, Actors, Plot, Poster, imdbID, trailer
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long movieId;
    private String title;
    private String releaseYear;
    private String posterImage;
    private String rated;
    private String runtime;
    private String genre;
    private String actors;
    private String plot;
    private String trailer;
    private String imdbId;
    private String imdbRating;
    private String imdbRatingCount;

    public Movie(String title, String releaseYear, String posterImage, String rated, String runtime, String genre, String actors, String plot, String trailer, String imdbId, String imdbRating, String imdbRatingCount) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterImage = posterImage;
        this.rated = rated;
        this.runtime = runtime;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        this.trailer = trailer;
        this.imdbId = imdbId;
        this.imdbRating = imdbRating;
        this.imdbRatingCount = imdbRatingCount;
    }

    public Movie(String title, String releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }
}
