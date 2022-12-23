package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
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
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.IN_PROGRESS;


    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "booking" //OneToMany benutzt IMMER mappedBy
    )
    @ToString.Exclude
    private List<Ticket> tickets = new ArrayList<>();

    public Booking(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking booking = (Booking) o;
        return id != null && Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

































