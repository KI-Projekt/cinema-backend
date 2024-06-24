package de.cinetastisch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiMovieRequestDTO {
    private Long movieId;
    private String title;
    private String description;
    private String genre;


}
