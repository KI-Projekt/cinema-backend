package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("select sum(t.price) from Order b join Ticket t on b.id = t.order.id where t.order.id = ?1")
//    Double totalCost();

    @Query("SELECT t FROM Order b join Ticket t on b.id = t.order.id where t.order.id = ?1 AND b.user.id = :userId")
    List<Ticket> getTicketsByUser(@Param("userId") Long userID);

    @Query("SELECT b FROM Order b where b.id = ?1 AND b.user.id = :userId")
    List<Order> getOrdersByUserId(@Param("userId") Long userId);
}
