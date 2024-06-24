package de.cinetastisch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class AiScoreUserRequestDTO {
    List<String> genres;
    List<AiRatingRequestDTO> ratings;
}
