package de.cinetastisch.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "movie_id_unique", columnNames = {"id"}),
        @UniqueConstraint(name = "movie_imdbId_unique", columnNames = {"imdbId"})
})
public class Movie {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "movie_sequence", sequenceName = "movie_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "movie_sequence")
    @Column(name = "id")
    private @Id Long id;

    private @NonNull @Column(columnDefinition = "TEXT") String title;
    private String releaseYear;
    private String posterImage;
    private String rated;
    private String runtime;
    private String genre;
    private @Column(columnDefinition = "TEXT") String actors;
    private @Column(columnDefinition = "TEXT") String plot;
    private String trailer;
    private String imdbId;
    private String imdbRating;
    private String imdbRatingCount;

    public Movie(@NonNull String title, String releaseYear, String posterImage, String rated, String runtime,
                 String genre,
                 String actors, String plot, String trailer, String imdbId, String imdbRating, String imdbRatingCount) {
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
        return id.equals(movie.id) && title.equals(movie.title) && Objects.equals(releaseYear, movie.releaseYear) && Objects.equals(posterImage, movie.posterImage) && Objects.equals(rated, movie.rated) && Objects.equals(runtime, movie.runtime) && Objects.equals(genre, movie.genre) && Objects.equals(actors, movie.actors) && Objects.equals(plot, movie.plot) && Objects.equals(trailer, movie.trailer) && Objects.equals(imdbId, movie.imdbId) && Objects.equals(imdbRating, movie.imdbRating) && Objects.equals(imdbRatingCount, movie.imdbRatingCount);}

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear, posterImage, rated, runtime, genre, actors, plot, trailer, imdbId, imdbRating, imdbRatingCount);
    }
}
