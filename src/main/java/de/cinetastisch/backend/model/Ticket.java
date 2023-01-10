package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.TicketCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Ticket")
@Table(name = "ticket")
public class Ticket {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "ticket_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id", foreignKey = @ForeignKey(name = "ticket_orders_id_fk"))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "ticket_screening_id_fk"))
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(name = "ticket_seat_id_fk"))
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private TicketCategory category = TicketCategory.ADULT;

    public Ticket(Order order, Screening screening, Seat seat, TicketCategory category) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.category = category;
    }
}
