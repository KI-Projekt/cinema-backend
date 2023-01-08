package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Reservation;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByExpireAtIsLessThanEqual(LocalDateTime time);

    List<Reservation> findAllByScreening(Screening screening);

    Optional<Reservation> findByUserAndScreeningAndSeat(User user, Screening screening, Seat seat);
    boolean existsByScreeningAndSeat(Screening screening, Seat seat);
}