package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.cinetastisch.backend.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Order")
@Table(name = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @SequenceGenerator(name = "orders_sequence",sequenceName = "orders_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "orders_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "order_user_id_fk"))
    private User user;

    @Column(name = "order_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.IN_PROGRESS;

    @Column(name = "is_paid",nullable = false)
    private Boolean isPaid = false;


    public Order(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, orderStatus);
    }
}
