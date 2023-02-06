package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {
}
