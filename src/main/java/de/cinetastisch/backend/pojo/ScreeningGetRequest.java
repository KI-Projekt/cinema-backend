package de.cinetastisch.backend.pojo;

import de.cinetastisch.backend.model.Movie;
import lombok.Data;

@Data
public class ScreeningGetRequest {
    private final String date;
    private final String timeSlot;
//    private final MovieInfo movieInfo;
    private final Long roomId;
}
