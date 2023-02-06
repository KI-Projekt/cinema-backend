package de.cinetastisch.backend.repository;

import de.cinetastisch.backend.model.TicketFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketFareRepository extends JpaRepository<TicketFare, Long>, JpaSpecificationExecutor<TicketFare> {
    TicketFare findByNameLikeIgnoreCase(String Name);
}
