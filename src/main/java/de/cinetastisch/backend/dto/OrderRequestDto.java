package de.cinetastisch.backend.dto;

import java.util.List;

public record OrderRequestDto(
        Long userId,
        Long screeningId,
        List<Long> ticketIds
) {
}
