package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByRoom(Room room);
    List<Seat> findAllByCategory(SeatCategory category);
    List<Seat> findAllByRoomAndCategory(Room room, SeatCategory category);

    boolean existsByRowAndColumn(Integer row, Integer column);
}
