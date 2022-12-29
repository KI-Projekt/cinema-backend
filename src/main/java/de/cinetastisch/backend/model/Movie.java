package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name="imdb_id_unique", columnNames={"imdbId"}))
public class Movie {

    @GeneratedValue(strategy = AUTO)
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
}
