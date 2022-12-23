package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Room")
@Table(name = "room", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Room.class
)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
//@Table(name = "room")
public class Room{

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


    @JsonManagedReference
    @OneToMany(
            mappedBy = "room",
//            targetEntity = Screening.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIdentityReference(alwaysAsId=true)
//    @JsonIgnore
    private List<Screening> screenings = new ArrayList<>();


    @OneToMany(
            mappedBy = "room",
//            targetEntity = Seat.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Seat> seats = new ArrayList<>();

    public Room(boolean isThreeD, boolean isDolbyAtmos) {
        this.isThreeD = isThreeD;
        this.isDolbyAtmos = isDolbyAtmos;
    }

//    public void addScreening(Screening screening){
////        if (!this.screenings.contains(screening)){
//            this.screenings.add(screening);
//            screening.setRoom(this);
////            return screening;
////        }
////        return null;
//    }

//    public void addSeat(Seat seat){
//        if (!seats.contains(seat)){
//            seats.add(seat);
//            seat.setRoom(this);
//        }
//    }
}
