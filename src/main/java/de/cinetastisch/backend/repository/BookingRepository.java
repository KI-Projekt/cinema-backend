package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Booking;
import de.cinetastisch.backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select sum(t.price) from Booking b join Ticket t on b.id = t.booking.id where t.booking.id = ?1")
    Double totalCost();

    @Query("SELECT t FROM Booking b join Ticket t on b.id = t.booking.id where t.booking.id = ?1 AND b.user.id = :userId")
    List<Ticket> getTicketsByUser(@Param("userId") Long userID);

    @Query("SELECT b FROM Booking b where b.id = ?1 AND b.user.id = :userId")
    List<Booking> getBookingsByUserId(@Param("userId") Long userId);
}
