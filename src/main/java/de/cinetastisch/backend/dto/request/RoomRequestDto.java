package de.cinetastisch.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record RoomRequestDto(
        @NotNull
        @NotBlank(message = "Name can't be empty.")
        String name,

        String hasThreeD,
        String hasDolbyAtmos,

        @Schema(nullable = true)
        Integer numberOfRows,
        @Schema(nullable = true)
        Integer numberOfColumns
) {}
