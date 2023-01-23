package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        UserResponseDto user,
        OrderStatus orderStatus,
        Double total,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        List<TicketResponseDto> tickets
) {
}
