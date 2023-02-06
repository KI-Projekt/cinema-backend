package de.cinetastisch.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table
public class OpeningHour {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "opening_hour_id", sequenceName = "opening_hour_id", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "opening_hour_id")
    @Column(name = "id")
    public @Id Long id;
    @Enumerated(EnumType.STRING)
    public DayOfWeek weekday;
    public LocalTime openingtime;
    public LocalTime closingtime;

    protected OpeningHour() {}

    public OpeningHour(DayOfWeek weekday, LocalTime openingtime, LocalTime closingtime) {
        this.weekday = weekday;
        this.openingtime = openingtime;
        this.closingtime = closingtime;
    }
}
