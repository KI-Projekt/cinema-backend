package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Seat")
@Table(name = "seat")
public class Seat { // Nicht in relation zu den

    @SequenceGenerator(name = "seat_sequence", sequenceName = "seat_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "seat_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "seat_room_id_fk"))
    private Room room;

    @Column(name = "seat_row", nullable = false)
    private Integer seatRow;

    @Column(name = "seat_column", nullable = false)
    private Integer seatColumn;

    @Column(name = "seat_type", nullable = false, columnDefinition = "TEXT")
    private String seatType;

    public Seat(Room room, Integer seatRow, Integer seatColumn, String seatType) {
        this.room = room;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatType = seatType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id.equals(seat.id) && Objects.equals(room, seat.room) && Objects.equals(seatRow, seat.seatRow) && Objects.equals(seatColumn, seat.seatColumn) && Objects.equals(seatType, seat.seatType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, seatRow, seatColumn, seatType);
    }
}
