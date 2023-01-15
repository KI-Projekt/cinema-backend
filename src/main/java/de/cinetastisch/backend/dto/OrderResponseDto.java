package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.Reservation;

import java.util.List;

public record OrderResponseDto(
        Long id,
        UserResponseDto user,
        OrderStatus orderStatus,
        Integer total,
        List<ReservationResponseDto> reservations,
        List<TicketResponseDto> tickets
) {
}
