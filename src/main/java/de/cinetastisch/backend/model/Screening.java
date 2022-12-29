package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Screening")
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Screening {

    @GeneratedValue(strategy = AUTO)
    private @Id Long id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_movie_id_fk"))
    @ToString.Exclude
    private Movie movie;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "screening_room_id_fk"))
    @ToString.Exclude
    private Room room;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Screening(Movie movie, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.movie = movie;
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
