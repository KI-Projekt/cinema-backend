package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findAllByMovie(Movie movie);
    List<Screening> findAllByRoom(Room room);

    @Query("SELECT s FROM Screening s WHERE ( s.startDateTime >= :from AND s.startDateTime <= :to ) " +
            "OR s.endDateTime >= :from AND s.endDateTime <= :to")
    List<Screening> findAllByLocalDateTimes(@Param("from") LocalDateTime from,@Param("to") LocalDateTime to);

    @Query("SELECT s FROM Screening s WHERE s.startDateTime >= :from")
    List<Screening> findAllAfterStartDateTime(@Param("from") LocalDateTime from);

    @Query("SELECT s FROM Screening s WHERE s.room.id = :roomId AND (( s.startDateTime >= :from AND s.startDateTime <= :to ) " +
            "OR (s.endDateTime >= :from AND s.endDateTime <= :to))")
    List<Screening> findAllByRoomAndTime(@Param("roomId") Long roomId,
                                         @Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);

}
