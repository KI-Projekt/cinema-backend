package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.enumeration.OrderStatus;

public record OrderResponseDto(
        Long id,
        UserResponseDto user,
        OrderStatus orderStatus,
        Integer total
) {
}
