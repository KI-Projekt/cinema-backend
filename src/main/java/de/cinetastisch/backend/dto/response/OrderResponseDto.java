package de.cinetastisch.backend.dto.response;

import de.cinetastisch.backend.enumeration.OrderPaymentMethod;
import de.cinetastisch.backend.enumeration.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record OrderResponseDto(
        Long id,
        UserResponseDto user,
        String session,
        OrderStatus orderStatus,
        Double total,
        OrderPaymentMethod paymentMethod,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        Map<String, Long> faresSelected,
        List<TicketResponseDto> tickets
) {
}
