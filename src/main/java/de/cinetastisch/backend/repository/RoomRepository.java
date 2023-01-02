package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.enumeration.RoomAudioExperience;
import de.cinetastisch.backend.enumeration.RoomScreenExperience;
import de.cinetastisch.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByNameIgnoreCase(String name);
    List<Room> findAllByNameLike(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsById(Long id);
}
