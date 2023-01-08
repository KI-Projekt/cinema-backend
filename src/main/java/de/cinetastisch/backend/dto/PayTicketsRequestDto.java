package de.cinetastisch.backend.dto;

import java.util.List;

public record PayTicketsRequestDto(
        Long orderId,
        List<Long> fares
) {
}
