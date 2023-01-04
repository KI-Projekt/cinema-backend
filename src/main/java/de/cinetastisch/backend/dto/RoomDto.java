package de.cinetastisch.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoomDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @NotNull
        @NotBlank(message = "Name can't be empty.")
        String name,

        String hasThreeD,
        String hasDolbyAtmos
) {}
