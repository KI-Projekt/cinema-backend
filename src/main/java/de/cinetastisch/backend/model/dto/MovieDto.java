package de.cinetastisch.backend.model.dto;

import de.cinetastisch.backend.model.Screening;
import jakarta.persistence.Column;

import java.util.List;

public class MovieDto {
    private Long id;
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
    private List<Screening> screenings;
}
