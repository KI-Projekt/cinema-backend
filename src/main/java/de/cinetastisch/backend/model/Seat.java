package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.cinetastisch.backend.enumeration.SeatCategory;
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
public class Seat {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "seat_sequence", sequenceName = "seat_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "seat_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "seat_room_id_fk"))
    private Room room;

    @Column(name = "seat_row", nullable = false)
    private Integer row;

    @Column(name = "seat_column", nullable = false)
    private Integer column;

    @Column(name = "seat_type", nullable = false, columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private SeatCategory category;

    public Seat(Room room, Integer row, Integer column, SeatCategory category) {
        this.room = room;
        this.row = row;
        this.column = column;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id.equals(seat.id) && room.equals(seat.room) && row.equals(seat.row) && column.equals(
                seat.column) && category == seat.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, row, column, category);
    }
}
