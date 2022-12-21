package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Data
@NoArgsConstructor
@Entity(name = "Screening")
@Table(name = "screening")
public class Screening {

    @Id
    @SequenceGenerator(
            name = "screening_sequence",
            sequenceName = "screening_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "screening_sequence"
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(
            name = "movie_id",
            foreignKey = @ForeignKey(name = "screening_movie_id_fk")
    )
    // @NotFound(action=NotFoundAction.IGNORE) // Auskommentieren wenn es beim Starten zu problemen gibt (Nach jeder @ManyToOne oder @OneToMany Annotation)
    private Movie movie;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(
            name = "room_id",
            foreignKey = @ForeignKey(name = "screening_room_id_fk")
    )
    private Room room; //TODO: Filmsaal-Entität erstellen

    @Column(
            name = "date",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String date;

    @Column(
            name = "time_slot",
            nullable = false,
            columnDefinition = "TEXT"
    )

    // @ManyToOne
    private String timeSlot; // statt genaue Uhrzeiten die Vorstellungen in Blöcke einteilen?


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "screening"
    )
    private List<Ticket> tickets = new ArrayList<>();


    public Screening(Movie movie, String date, Room room, String timeSlot) {
        this.movie = movie;
        this.date = date;
        this.room = room;
        this.timeSlot = timeSlot;
    }
}
