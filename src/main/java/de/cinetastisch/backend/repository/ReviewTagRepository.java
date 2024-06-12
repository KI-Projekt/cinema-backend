package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {
}
