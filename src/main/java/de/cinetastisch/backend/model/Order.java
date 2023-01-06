package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.OrderStatus;
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
@Entity
@Table(name = "orders")
public class Order {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "orders_sequence", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "orders_sequence")
    @Column(name = "id")
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
