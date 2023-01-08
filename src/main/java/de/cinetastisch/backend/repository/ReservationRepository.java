package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByExpiresAtIsLessThanEqual(LocalDateTime time);
    void deleteByScreeningAndUser(Screening screening, User user); // Delete all reservations of user by screening
    boolean existsByExpiresAtIsLessThanEqual(LocalDateTime time); // Gibt es Reservationen zu löschen?
    List<Reservation> findAllByExpiresAtLessThanEqual(LocalDateTime time);

    void deleteAllByOrder(Order order);

    @Query("SELECT DISTINCT r.order FROM Reservation r WHERE ?1 >= r.expiresAt")
    List<Order> getAllDistinctOrdersOfReservationsToDelete(LocalDateTime time);

    List<Reservation> findAllByOrder(Order order);
    List<Reservation> findAllByScreening(Screening screening);
    List<Reservation> findAllByUser(User user);
    List<Reservation> findAllByUserAndScreening(User user, Screening screening);
//    Iterable<Reservation> findAllByUserAndScreening(User user, Screening screening);

    Optional<Reservation> findByUserAndScreeningAndSeat(User user, Screening screening, Seat seat);
    Optional<Reservation> findByScreeningAndSeat(Screening screening, Seat seat);

    boolean existsByScreeningAndSeat(Screening screening, Seat seat);
    boolean existsByUserAndScreening(User user, Screening screening);

    boolean existsByScreeningAndSeatAndExpiresAtIsGreaterThanEqual(Screening screening, Seat seat, LocalDateTime time);

}