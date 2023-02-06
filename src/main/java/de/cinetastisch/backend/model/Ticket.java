package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id", foreignKey = @ForeignKey(name = "ticket_orders_id_fk"))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "screening_id", foreignKey = @ForeignKey(name = "ticket_screening_id_fk"))
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(name = "ticket_seat_id_fk"))
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "ticket_fare_id", foreignKey = @ForeignKey(name = "ticket_ticket_fare_id_fk"))
    private TicketFare selectedFare;

    @Enumerated(EnumType.STRING)
    private TicketType type = TicketType.RESERVATION;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private boolean deleted = Boolean.FALSE;

    public Ticket(Order order, Screening screening, Seat seat) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
    }

    public Ticket(Order order, Screening screening, Seat seat, TicketFare selectedFare) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.selectedFare = selectedFare;
    }

    public Ticket(Order order, Screening screening, Seat seat, TicketFare selectedFare, TicketType type) {
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.selectedFare = selectedFare;
        this.type = type;
    }

    public Ticket(Long id, Order order, Screening screening, Seat seat, TicketFare selectedFare, TicketType type, boolean deleted) {
        this.id = id;
        this.order = order;
        this.screening = screening;
        this.seat = seat;
        this.selectedFare = selectedFare;
        this.type = type;
        this.deleted = deleted;
    }

    @PrePersist // benefit of this would be marginal since usually you do not deal much with entity after persisting
    @PostLoad
    private void updateStatus() {
        if (!this.deleted && this.order != null && this.order.getStatus() == OrderStatus.CANCELLED){
            System.out.println(this);
            this.deleted = true;
            System.out.println(this);
        }

        if(this.getScreening().getStatus() == ScreeningStatus.CANCELLED){
            this.deleted = true;
        }
    }
}
