package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.cinetastisch.backend.enumeration.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString(exclude = {"tickets"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "orders_sequence", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "orders_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "order_user_id_fk"))
    private User user;

    private String session;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "order_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.IN_PROGRESS;

    private Double total = 0.00;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime expiresAt = this.createdAt.plusMinutes(1);

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
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

//    public Double getTotal() {
//        double newTotal = 0.0;
//        if(this.status == OrderStatus.IN_PROGRESS){
//            for(Ticket t : this.tickets){
//                newTotal = switch (t.getCategory()) {
//                    case KID        -> newTotal + 8;
//                    case STUDENT    -> newTotal + 10;
//                    case ADULT      -> newTotal + 12;
//                    case PENSIONER  -> newTotal + 11;
//                };
//            }
//            this.total = newTotal;
//        }
//
//        return total;
//    }

//    public OrderStatus getStatus() {
//        if(this.status == OrderStatus.IN_PROGRESS && LocalDateTime.now().isAfter(this.expiresAt)){
//            this.status = OrderStatus.CANCELLED;
//        }
//        return status;
//    }

    @PrePersist // benefit of this would be marginal since usually you do not deal much with entity after persisting
    @PostLoad
    private void updateStatusAndTotal() {
        if (this.status == OrderStatus.IN_PROGRESS && expiresAt.isBefore(LocalDateTime.now())){
            this.status = OrderStatus.CANCELLED;
        }

        if(this.status == OrderStatus.IN_PROGRESS){
            double newTotal = 0.0;
            for(Ticket t : this.tickets){
                newTotal = switch (t.getCategory()) {
                    case KID        -> newTotal + 8;
                    case STUDENT    -> newTotal + 10;
                    case ADULT      -> newTotal + 12;
                    case PENSIONER  -> newTotal + 11;
                };
            }
            this.total = newTotal;
        }
    }
}
