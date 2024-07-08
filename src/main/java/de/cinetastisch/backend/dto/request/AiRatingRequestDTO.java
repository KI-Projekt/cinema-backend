package de.cinetastisch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRatingRequestDTO {
    private Long externalId;
    private String title;
    private int year;
    private double rating;
}
