package de.cinetastisch.backend.model.info;

import lombok.Data;

@Data
public class ScreeningInfo {
    private final Long movieId;
    private final Long roomId;
    private final String date;
    private final String timeSlot;
}
