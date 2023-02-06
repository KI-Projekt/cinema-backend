package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long>, JpaSpecificationExecutor<Screening> {

    @Query("SELECT s FROM Screening s WHERE s.room = :room AND (( s.startDateTime >= :from AND s.startDateTime <= :to ) OR ( s.endDateTime >= :from AND s.endDateTime <= :to ) OR (:from >= s.startDateTime AND :to <= s.endDateTime))")
    List<Screening> findAllByRoomAndTime(@Param("room") Room room,
                                         @Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);

    boolean existsByMovie(Movie movie);

}
