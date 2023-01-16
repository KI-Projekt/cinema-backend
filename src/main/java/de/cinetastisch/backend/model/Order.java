package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.cinetastisch.backend.enumeration.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString(exclude = {"tickets", "reservations"})
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "order_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.IN_PROGRESS;

    private Integer total;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    public Order(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && Objects.equals(user,
                                                     order.user) && orderStatus == order.orderStatus && Objects.equals(
                total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, orderStatus, total);
    }
}
