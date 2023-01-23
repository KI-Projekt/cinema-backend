package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("select sum(t.price) from Order b join Ticket t on b.id = t.order.id where t.order.id = ?1")
//    Double totalCost();

    List<Order> findAllByUser(User user);
    List<Order> findAllByStatus(OrderStatus orderStatus);
    List<Order> findAllByUserAndStatus(User user, OrderStatus orderStatus);


    boolean existsByUserAndStatus(User user, OrderStatus status);

}
