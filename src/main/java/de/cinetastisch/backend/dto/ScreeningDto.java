package de.cinetastisch.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.pojo.OmdbMovieResponse;

import java.time.LocalDateTime;

public record ScreeningDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @JsonProperty(defaultValue = "TICKET_SALE_OPEN")
        ScreeningStatus status,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        MovieDto movie,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
        Long movieId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Room room,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
        Long roomId,

        @JsonProperty(required = true)
        LocalDateTime startDateTime,

        LocalDateTime endDateTime)
{}
