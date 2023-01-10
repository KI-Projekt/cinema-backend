package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.ReservationRequestDto;
import de.cinetastisch.backend.dto.ReservationResponseDto;
import de.cinetastisch.backend.model.Reservation;
import de.cinetastisch.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @Operation(
            tags = {"Reservations"}
    )
    @GetMapping
    public List<ReservationResponseDto> getAll(@RequestParam(value = "userId", required = false) Long userId,
                                               @RequestParam(value = "screeningId", required = false) Long screeningId){
        return reservationService.getAllReservations(userId, screeningId);
    }

    @Operation(
            tags = {"Reservations"}
    )
    @PostMapping
    public ResponseEntity<ReservationResponseDto> addReservation(@Valid @RequestBody ReservationRequestDto request){
        return new ResponseEntity<>(reservationService.addReservation(request), HttpStatus.CREATED);
    }
}