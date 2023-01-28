package de.cinetastisch.backend.dto.response;

import de.cinetastisch.backend.dto.response.SeatResponseDto;

public record ScreeningSeatDto(
        SeatResponseDto seat,
        boolean reserved
){
}
