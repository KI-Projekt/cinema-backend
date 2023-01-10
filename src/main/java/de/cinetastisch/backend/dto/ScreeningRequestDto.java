package de.cinetastisch.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScreeningRequestDto(
        @NotNull @NotEmpty Long movieId,
        @NotNull @NotEmpty Long roomId,
        @NotNull @NotEmpty LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        @JsonProperty(defaultValue = "TICKET_SALE_OPEN") String status
) {}