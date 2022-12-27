package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Movie")
@Table(name = "movie", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @SequenceGenerator(name = "movie_sequence",sequenceName = "movie_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "movie_sequence")
    @Column(name = "id")
    private @Id Long id;

    @Column(name = "title",nullable = false,columnDefinition = "TEXT")
    private String title;

    @Column(name = "release_year",nullable = false,columnDefinition = "TEXT")
    private String releaseYear;

    @Column(name = "poster_image",nullable = false,columnDefinition = "TEXT")
    private String posterImage;

    @Column(name = "rated",nullable = false,columnDefinition = "TEXT")
    private String rated;

    @Column(name = "runtime",nullable = false,columnDefinition = "TEXT")
    private String runtime;

    @Column(name = "genre",nullable = false,columnDefinition = "TEXT")
    private String genre;

    @Column(name = "actors",nullable = false,columnDefinition = "TEXT")
    private String actors;

    @Column(name = "plot",nullable = false,columnDefinition = "TEXT")
    private String plot;

    @Column(name = "trailer",nullable = false,columnDefinition = "TEXT")
    private String trailer;

    @Column(name = "imdb_id",nullable = false,columnDefinition = "TEXT")
    private String imdbId;

    @Column(name = "imdb_rating",nullable = false,columnDefinition = "TEXT")
    private String imdbRating;

    @Column(name = "imdb_rating_count",nullable = false,columnDefinition = "TEXT")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) && title.equals(movie.title) && Objects.equals(releaseYear, movie.releaseYear) && Objects.equals(posterImage, movie.posterImage) && Objects.equals(rated, movie.rated) && Objects.equals(runtime, movie.runtime) && Objects.equals(genre, movie.genre) && Objects.equals(actors, movie.actors) && Objects.equals(plot, movie.plot) && Objects.equals(trailer, movie.trailer) && Objects.equals(imdbId, movie.imdbId) && Objects.equals(imdbRating, movie.imdbRating) && Objects.equals(imdbRatingCount, movie.imdbRatingCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear, posterImage, rated, runtime, genre, actors, plot, trailer, imdbId, imdbRating, imdbRatingCount);
    }
}
