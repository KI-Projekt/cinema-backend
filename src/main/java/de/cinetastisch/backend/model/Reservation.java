package de.cinetastisch.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "reservation_id_unique", columnNames = {"id"})
})
public class Reservation {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "reservation_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_user_id_fk"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_screening_id_fk"))
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_seat_id_fk"))
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_order_id_fk"))
    private Order order;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(20L);

    public Reservation(User user, Screening screening, Seat seat, Order order) {
        this.user = user;
        this.screening = screening;
        this.seat = seat;
        this.order = order;
    }
}

