package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Ticket")
@Table(name = "ticket")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "ticket_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id", foreignKey = @ForeignKey(name = "ticket_orders_id_fk"))
    @JsonIgnore
    private Order order;

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

    public Ticket(Order order, Screening screening, Seat seat, Double price) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id) && Objects.equals(order, ticket.order) && Objects.equals(price, ticket.price) && Objects.equals(screening, ticket.screening) && Objects.equals(seat, ticket.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, price, screening, seat);
    }
}
