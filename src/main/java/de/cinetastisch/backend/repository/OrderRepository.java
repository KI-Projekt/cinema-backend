package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("select sum(t.price) from Order b join Ticket t on b.id = t.order.id where t.order.id = ?1")
//    Double totalCost();

    List<Order> findAllByUser(User user);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByUserAndOrderStatus(User user, OrderStatus orderStatus);

}
