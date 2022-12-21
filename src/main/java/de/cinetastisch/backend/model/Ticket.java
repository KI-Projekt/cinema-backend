package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Data
@NoArgsConstructor
@Entity(name = "Ticket")
@Table(name = "ticket")
public class Ticket {
    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ticket_sequence"
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(
            name = "booking_id",
            foreignKey = @ForeignKey(
                    name = "ticket_booking_id_fk"
            )
    )
    private Booking booking;


    @ManyToOne
    @MapsId("screeningId")
    @JoinColumn(
            name = "screening_id",
            foreignKey = @ForeignKey(
                    name = "ticket_screening_id_fk"
            )
    )
    private Screening screening;

}
