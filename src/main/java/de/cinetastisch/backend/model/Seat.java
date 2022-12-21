package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Entity(name = "Seat")
@Table(name = "seat")
public class Seat { // Nicht in relation zu den

    @Id
    @SequenceGenerator(
            name = "seat_sequence",
            sequenceName = "seat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "seat_sequence"
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(
            name = "room_id",
            foreignKey = @ForeignKey(
                    name = "seat_room_id_fk"
            )
    )
    private Room room;

    @Column(
            name = "seat_row",
            nullable = false
    )
    private Integer seatRow;

    @Column(
            name = "seat_column",
            nullable = false
    )
    private Integer seatColumn;

    @Column(
            name = "seat_type",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String seatType; // Standard, Premium


    public Seat(Room room, Integer seatRow, Integer seatColumn, String seatType) {
        this.room = room;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatType = seatType;
    }
}
