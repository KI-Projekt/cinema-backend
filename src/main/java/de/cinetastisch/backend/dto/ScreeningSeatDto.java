package de.cinetastisch.backend.dto;

public record ScreeningSeatDto(
        SeatDto seat,
        boolean reserved
){
}
