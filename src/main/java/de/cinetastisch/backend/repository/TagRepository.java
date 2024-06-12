package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTag(String name);

    Tag findByTag(String name);
}
