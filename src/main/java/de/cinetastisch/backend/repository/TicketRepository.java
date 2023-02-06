package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByScreening(Screening screening);
    List<Ticket> findAllByOrder(Order order);

    boolean existsByScreeningAndSeat(Screening screening, Seat seat);

    void deleteAllByOrderStatusOrOrderExpiresAtIsLessThan(OrderStatus status, LocalDateTime now);
}
