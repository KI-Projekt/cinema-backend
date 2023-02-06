package de.cinetastisch.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequestDto(
        @NotNull @NotEmpty
        String firstName,
        @NotNull @NotEmpty
        String lastName,
        @NotNull @NotEmpty
        String email,
        @NotNull @NotEmpty
        String password,
        @NotNull @NotEmpty
        String matchingPassword,
        @NotNull @NotEmpty
        LocalDate birthday,
        String country,
        String city,
        String zip,
        String street,
        Integer houseNumber
) {
}
