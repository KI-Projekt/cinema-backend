package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
