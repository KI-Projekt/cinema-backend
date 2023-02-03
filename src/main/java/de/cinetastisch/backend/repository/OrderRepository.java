package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);
    List<Order> findAllByStatus(OrderStatus orderStatus);

    Order findByUserAndStatus(User user, OrderStatus status);
    Order findBySessionAndStatus(String session, OrderStatus status);


    boolean existsByUserAndStatus(User user, OrderStatus status);
    boolean existsBySessionAndStatus(String session, OrderStatus status);
}
