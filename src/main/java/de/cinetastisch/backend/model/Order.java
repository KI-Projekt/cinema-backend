package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @GeneratedValue(strategy = AUTO)
    private @Id Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "order_user_id_fk"))
    private User user;

    @Column(name = "order_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.IN_PROGRESS;

    public Order(User user) {
        this.user = user;
    }
}
