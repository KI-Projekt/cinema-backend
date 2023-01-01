package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Seat")
@Table(name = "seat")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Seat {

    @Schema(accessMode = READ_ONLY)
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
