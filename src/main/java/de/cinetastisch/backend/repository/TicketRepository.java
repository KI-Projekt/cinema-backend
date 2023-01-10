package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByOrder(Order order);
    List<Ticket> findAllByScreening(Screening screening);

    Optional<Ticket> findByScreeningAndSeat(Screening screening, Seat seat);

    boolean existsByScreeningAndSeat(Screening screening, Seat seat);
}
