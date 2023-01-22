package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.OrderStatus;

import java.util.List;

public record OrderResponseDto(
        Long id,
        UserResponseDto user,
        OrderStatus orderStatus,
        Double total,
        List<ReservationResponseDto> reservations,
        List<TicketResponseDto> tickets
) {
}
