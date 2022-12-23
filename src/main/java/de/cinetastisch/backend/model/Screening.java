package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Screening")
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
//@Table(name = "screening")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Screening.class
)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Screening{

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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//    @MapsId("movieId")  maps-id du hs
    @JoinColumn(
            name = "movie_id",
            nullable = true,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "screening_movie_id_fk")
    )
    @ToString.Exclude
    private Movie movie;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "room_id",
            nullable = true,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "screening_room_id_fk")
    )//    @MapsId("roomId")   DU WI***
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
    private String timeSlot; // statt genaue Uhrzeiten die Vorstellungen in Blöcke einteilen?


//    @OneToMany(
//            cascade = {CascadeType.ALL},
//            mappedBy = "screening"
//    )
//    @ToString.Exclude
//    private List<Ticket> tickets = new ArrayList<>();

    public Screening(String date, String timeSlot) {
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public Screening(Movie movie, Room room, String date, String timeSlot) {
        this.movie = movie;
        this.room = room;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    //    public void addTicket(Ticket ticket){
//        if (!tickets.contains(ticket)) {
//            tickets.add(ticket);
//            ticket.setScreening(this);
//        }
//    }


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
