package de.cinetastisch.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto{
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Nullable
    private Long id;

    @NotNull
    @NotBlank
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

    public MovieDto(String title, String releaseYear, String posterImage, String rated, String runtime, String genre,
                    String actors, String plot, String trailer, String imdbId, String imdbRating,
                    String imdbRatingCount) {
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
