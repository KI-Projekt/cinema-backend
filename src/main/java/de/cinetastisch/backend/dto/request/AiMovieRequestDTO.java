package de.cinetastisch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiMovieRequestDTO {
    private Long externalId;
    private String title;
    private int year;


}
