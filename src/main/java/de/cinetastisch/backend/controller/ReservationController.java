package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.ReservationRequestDto;
import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.dto.response.TicketResponseDto;
import de.cinetastisch.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<TicketResponseDto>> getAll(@RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(value = "screeningId", required = false) Long screeningId){
        return ResponseEntity.ok(reservationService.getAllReservations(userId, screeningId));
    }

    @Operation(
            tags = {"Reservations"}
    )
    @PostMapping
    public ResponseEntity<OrderResponseDto> addReservation(@Valid @RequestBody ReservationRequestDto request, HttpServletRequest principal){
        return new ResponseEntity<>(reservationService.addReservation(request, principal), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Reservations"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@Valid @PathVariable Long id){
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}