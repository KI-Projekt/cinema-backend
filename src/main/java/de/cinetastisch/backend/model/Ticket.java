package de.cinetastisch.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Ticket")
@Table(name = "ticket")
public class Ticket {

    @SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "ticket_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "ticket_booking_id_fk"))
    private Booking booking;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @MapsId("screeningId")
    @JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "ticket_screening_id_fk"))
    private Screening screening;

    @ManyToOne
    @MapsId("seatId")
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(name = "ticket_seat_id_fk"))
    private Seat seat;

    public Ticket(Booking booking, Screening screening, Seat seat, Double price) {
        this.booking = booking;
        this.screening = screening;
        this.seat = seat;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id) && Objects.equals(booking, ticket.booking) && Objects.equals(price, ticket.price) && Objects.equals(screening, ticket.screening) && Objects.equals(seat, ticket.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, booking, price, screening, seat);
    }
}
