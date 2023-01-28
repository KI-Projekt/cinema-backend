package de.cinetastisch.backend.dto.request;

public record FaresDto (
        Integer kidsCount,
        Integer studentCount,
        Integer adultsCount,
        Integer pensionerCount
) {
}
