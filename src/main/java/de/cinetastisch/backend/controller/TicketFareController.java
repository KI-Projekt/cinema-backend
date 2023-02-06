package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.TicketFare;
import de.cinetastisch.backend.repository.TicketFareRepository;
import io.swagger.v3.oas.annotations.Operation;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fareSelection")
public class TicketFareController {

    private final TicketFareRepository ticketFareRepository;

    public TicketFareController(TicketFareRepository ticketFareRepository) {
        this.ticketFareRepository = ticketFareRepository;
    }

    @Operation(
            tags = {"TicketFares"}
    )
    @Transactional
    @GetMapping
    public ResponseEntity<List<TicketFare>> getAll(
            @And({
                    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class)
            }) Specification<TicketFare> spec){
        return ResponseEntity.ok(ticketFareRepository.findAll(spec));
    }

    @Operation(
            tags = {"TicketFares"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<TicketFare> getOneById(@PathVariable("id") Long id){
        return ResponseEntity.ok(ticketFareRepository.getReferenceById(id));
    }

    @Operation(
            tags = {"TicketFares"}
    )
    @PostMapping
    public ResponseEntity<TicketFare> addOne(@RequestBody TicketFare ticketFare){
        ticketFare.setId(null);
        return new ResponseEntity<>(ticketFareRepository.save(ticketFare), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"TicketFares"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<TicketFare> replaceOne(@PathVariable("id") Long id, @RequestBody TicketFare ticketFare){
        if(!id.equals(ticketFare.getId())){
            throw new IllegalArgumentException("IDs do not match");
        }
        return ResponseEntity.ok(ticketFareRepository.save(ticketFare));
    }

    @Operation(
            tags = {"TicketFares"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id){
        ticketFareRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
