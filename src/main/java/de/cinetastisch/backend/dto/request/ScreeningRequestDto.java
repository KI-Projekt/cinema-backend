package de.cinetastisch.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScreeningRequestDto(
        @NotNull @NotEmpty Long movieId,
        @NotNull @NotEmpty Long roomId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String isThreeD,
        String isDolbyAtmos,
        @JsonProperty(defaultValue = "TICKET_SALE_OPEN") String status
) {}