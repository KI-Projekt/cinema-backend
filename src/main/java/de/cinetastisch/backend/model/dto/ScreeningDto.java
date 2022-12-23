package de.cinetastisch.backend.model.dto;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import lombok.Data;

@Data
public class ScreeningDto {
    private Long id;
    private Movie movie;
    private Room room;
    private String date;
    private String timeSlot;
}
