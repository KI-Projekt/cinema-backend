package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Screening")
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Screening {

    @SequenceGenerator(name = "screening_sequence", sequenceName = "screening_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "screening_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = true, referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_movie_id_fk"))
    @ToString.Exclude
    private Movie movie;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = true, referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_room_id_fk"))
    @ToString.Exclude
    private Room room;

    @Column(name = "date", nullable = false, columnDefinition = "TEXT")
    private java.time.LocalDate date;

    @Column(name = "start_time", nullable = false, columnDefinition = "TEXT")
    private java.time.LocalTime startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "TEXT")
    private java.time.LocalTime endTime;

    public Screening(Movie movie, Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.movie = movie;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
