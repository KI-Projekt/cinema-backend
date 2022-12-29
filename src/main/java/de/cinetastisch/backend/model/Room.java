package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import de.cinetastisch.backend.enumeration.RoomAudioExperience;
import de.cinetastisch.backend.enumeration.RoomScreenExperience;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Room")
@Table(name = "room", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    @SequenceGenerator(name = "room_sequence", sequenceName = "room_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "room_sequence")
    @Column(name = "id")
    private @Id Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoomScreenExperience roomScreenExperience = RoomScreenExperience.TWO_D;

    @Enumerated(EnumType.STRING)
    private RoomAudioExperience roomAudioExperience = RoomAudioExperience.STANDARD;


    public Room(RoomScreenExperience roomScreenExperience, RoomAudioExperience roomAudioExperience) {
        this.roomScreenExperience = roomScreenExperience;
        this.roomAudioExperience = roomAudioExperience;
    }

    public Room(String roomScreenExperience, String roomAudioExperience) { // Backup
        this.roomScreenExperience = RoomScreenExperience.valueOf(roomScreenExperience);
        this.roomAudioExperience = RoomAudioExperience.valueOf(roomAudioExperience);
    }

    public Room(String name, RoomScreenExperience roomScreenExperience, RoomAudioExperience roomAudioExperience) {
        this.name = name;
        this.roomScreenExperience = roomScreenExperience;
        this.roomAudioExperience = roomAudioExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id) && Objects.equals(name,
                                                    room.name) && roomScreenExperience == room.roomScreenExperience && roomAudioExperience == room.roomAudioExperience;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roomScreenExperience, roomAudioExperience);
    }
}
