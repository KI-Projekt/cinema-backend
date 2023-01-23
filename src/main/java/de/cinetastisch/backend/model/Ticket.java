package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.TicketCategory;
import de.cinetastisch.backend.enumeration.TicketType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Ticket")
@Table(name = "ticket")
@SQLDelete(sql = "UPDATE ticket SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Ticket {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "ticket_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orders_id", foreignKey = @ForeignKey(name = "ticket_orders_id_fk"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "ticket_screening_id_fk"))
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(name = "ticket_seat_id_fk"))
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private TicketCategory category = TicketCategory.ADULT;

    @Enumerated(EnumType.STRING)
    private TicketType type = TicketType.RESERVATION;

    private final LocalDateTime createdAt = LocalDateTime.now();
//    private final LocalDateTime expiresAt = this.order.getExpiresAt();

    private boolean deleted = Boolean.FALSE;

    public Ticket(Order order, Screening screening, Seat seat) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
    }

    public Ticket(Order order, Screening screening, Seat seat, TicketCategory category) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.category = category;
    }

    public Ticket(Order order, Screening screening, Seat seat, TicketCategory category, TicketType type) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.category = category;
        this.type = type;
    }
}
