package de.cinetastisch.backend.dto;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScreeningDto {
    private Long id;
    private Movie movie;
    private Room room;
    private LocalDate date;
    private LocalTime startDateTime;
    private LocalTime endDateTime;
}
