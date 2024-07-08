package de.cinetastisch.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiScoreMoviesResponseDTO {

    private List<AiScoreResponseDTO> movies;
}
