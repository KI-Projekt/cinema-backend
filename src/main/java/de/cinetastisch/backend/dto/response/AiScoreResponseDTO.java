package de.cinetastisch.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiScoreResponseDTO {
    private Long externalId;
    private double score;
    private String title;
    private int year;
}
