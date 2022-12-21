package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Entity(name = "Movie")
@Table(name = "movie")
public class Movie { //Title, Rated, Runtime, Genre, Director, Actors, Plot, Poster, imdbID, trailer

    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "movie_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name = "release_year",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String releaseYear;

    @Column(
            name = "poster_image",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String posterImage;

    @Column(
            name = "rated",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String rated;

    @Column(
            name = "runtime",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String runtime;

    @Column(
            name = "genre",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String genre;

    @Column(
            name = "actors",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String actors;

    @Column(
            name = "plot",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String plot;

    @Column(
            name = "trailer",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String trailer;

    @Column(
            name = "imdb_id",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imdbId;

    @Column(
            name = "imdb_rating",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imdbRating;

    @Column(
            name = "imdb_rating_count",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imdbRatingCount;


    @OneToMany(
            cascade = {CascadeType.ALL}, // oder {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "movie"
    )
    private List<Screening> screenings = new ArrayList<>();


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

}
