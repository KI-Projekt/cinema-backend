package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Entity(name = "Booking")
@Table(name = "booking")
public class Booking {

    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "booking_sequence"
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "booking_user_id_fk"
            )
    )
    private User user;

    @Column(
            name = "booking_status",
            nullable = false
    )
    private BookingStatus bookingStatus;


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "booking" //OneToMany benutzt IMMER mappedBy
    )
    private List<Ticket> tickets = new ArrayList<>();
}

































