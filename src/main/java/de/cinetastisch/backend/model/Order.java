package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.OrderPaymentMethod;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString(exclude = {"tickets"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @SequenceGenerator(name = "orders_sequence", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "orders_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "order_user_id_fk"))
    @Nullable
    private User user;

    private String session;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.IN_PROGRESS;

    private Double total = 0.00;
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderPaymentMethod paymentMethod = OrderPaymentMethod.PAYPAL;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt = this.createdAt.plusMinutes(5);

    @OneToMany(mappedBy = "order")
    @OrderBy(value = "order.id, seat.id ASC")
    private List<Ticket> tickets = new ArrayList<>();

    public Order(User user) {
        this.user = user;
    }

    public Order(String session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && Objects.equals(user, order.user) && total.equals(
                order.total) && status == order.status && createdAt.equals(order.createdAt) && Objects.equals(
                tickets, order.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, total, status, createdAt, tickets);
    }



    @PrePersist // benefit of this would be marginal since usually you do not deal much with entity after persisting
    @PostLoad
    private void updateTotalAndStatus() {
        if (this.status == OrderStatus.IN_PROGRESS){
            double newTotal = 0.0;
            for(Ticket t : this.tickets){
                newTotal += t.getSelectedFare().getPrice();
            }
            this.total = newTotal;

            if(expiresAt.isBefore(LocalDateTime.now())){
                this.status = OrderStatus.CANCELLED;
                this.tickets.forEach(ticket -> ticket.setDeleted(true));
                this.tickets = new ArrayList<>();
            }

        }

        if(this.tickets.size() > 0 && this.tickets.get(0).getScreening().getStatus() == ScreeningStatus.CANCELLED){
            this.status = this.status == OrderStatus.PAID ? OrderStatus.REFUNDED : OrderStatus.CANCELLED;
            this.tickets.forEach(ticket -> ticket.setDeleted(true));
            this.tickets = new ArrayList<>();
        }
    }
}
