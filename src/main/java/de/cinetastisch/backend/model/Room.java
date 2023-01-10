package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "room_id_unique", columnNames = {"id"}),
        @UniqueConstraint(name = "room_name_unique" , columnNames = {"name"})
        })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "room_sequence", sequenceName = "room_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "room_sequence")
    @Column(name = "id")
    private @Id Long id;

    private @NonNull String name;
    private Boolean hasThreeD;
    private Boolean hasDolbyAtmos;


    public Room(@NonNull String name, Boolean hasThreeD, Boolean hasDolbyAtmos) {
        this.name = name;
        this.hasThreeD = hasThreeD;
        this.hasDolbyAtmos = hasDolbyAtmos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id) && name.equals(room.name) && Objects.equals(hasThreeD,
                                                                              room.hasThreeD) && Objects.equals(
                hasDolbyAtmos, room.hasDolbyAtmos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hasThreeD, hasDolbyAtmos);
    }
}
