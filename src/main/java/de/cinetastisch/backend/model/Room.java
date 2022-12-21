package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Entity(name = "Room")
@Table(name = "room")
public class Room {

    @Id
    @SequenceGenerator(
            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "room_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(
            name = "is_three_d",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private boolean isThreeD;

    @Column(
            name = "is_dolby_atmos",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private boolean isDolbyAtmos;


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "room" //OneToMany benutzt IMMER mappedBy
    )
    private List<Screening> screenings = new ArrayList<>();

    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "room" //OneToMany benutzt IMMER mappedBy
    )
    private List<Seat> seats = new ArrayList<>();
}
