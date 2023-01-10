package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByRoom(Room room);
    List<Seat> findAllByCategory(SeatCategory category);
    List<Seat> findAllByRoomAndCategory(Room room, SeatCategory category);

    List<Seat> findAllByRoomOrderByRowDesc(Room room);
    List<Seat> findAllByRoomOrderByColumnDesc(Room room);

    List<Seat> findAllByRoomAndRowOrderByColumnDesc(Room room, Integer row);

    Optional<Seat> findByRowAndColumn(Integer seatRow, Integer seatColumn);

    boolean existsByRowAndColumn(Integer row, Integer column);
}
