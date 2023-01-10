package de.cinetastisch.backend.dto;

public record ScreeningSeatDto(
        SeatResponseDto seat,
        boolean reserved
){
}
