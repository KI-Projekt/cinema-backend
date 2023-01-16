package de.cinetastisch.backend.dto;

public record FaresDto (
        Integer kidsCount,
        Integer studentCounts,
        Integer adultsCount,
        Integer pensionerCount
) {
}
