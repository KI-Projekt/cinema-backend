package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Screening")
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
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
    private String date;

    @Column(name = "time_slot", nullable = false, columnDefinition = "TEXT")
    private String timeSlot;

    public Screening(Movie movie, Room room, String date, String timeSlot) {
        this.movie = movie;
        this.room = room;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screening screening = (Screening) o;
        return id.equals(screening.id) && Objects.equals(date, screening.date) && Objects.equals(timeSlot, screening.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, timeSlot);
    }
}
