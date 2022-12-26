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
@Entity(name = "Room")
@Table(name = "room", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Room {

    @SequenceGenerator(name = "room_sequence", sequenceName = "room_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "room_sequence")
    @Column(name = "id")
    private @Id Long id;

    @Column(name = "is_three_d", nullable = false, columnDefinition = "TEXT")
    private boolean isThreeD;

    @Column(name = "is_dolby_atmos", nullable = false, columnDefinition = "TEXT")
    private boolean isDolbyAtmos;

    public Room(boolean isThreeD, boolean isDolbyAtmos) {
        this.isThreeD = isThreeD;
        this.isDolbyAtmos = isDolbyAtmos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return isThreeD == room.isThreeD && isDolbyAtmos == room.isDolbyAtmos && id.equals(room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isThreeD, isDolbyAtmos);
    }
}
