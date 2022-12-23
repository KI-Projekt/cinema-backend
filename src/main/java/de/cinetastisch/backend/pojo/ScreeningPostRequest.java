package de.cinetastisch.backend.pojo;

import lombok.Data;

@Data
public class ScreeningPostRequest {
    private final String date;
    private final String timeSlot;
    private final Long movieId;
    private final Long roomId;
}
