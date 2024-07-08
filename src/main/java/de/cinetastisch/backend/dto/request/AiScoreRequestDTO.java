package de.cinetastisch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class AiScoreRequestDTO {

    List<AiMovieRequestDTO> movies;
    List<AiRatingRequestDTO> user_ratings;
}
