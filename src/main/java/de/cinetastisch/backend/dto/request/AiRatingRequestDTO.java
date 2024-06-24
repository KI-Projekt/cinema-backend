package de.cinetastisch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRatingRequestDTO {
    private String movieTitle;
    private double rating;
}
