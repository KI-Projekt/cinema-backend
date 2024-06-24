package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Review;
import de.cinetastisch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser(User user);
}
