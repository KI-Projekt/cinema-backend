package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.ReservationRequestDto;
import de.cinetastisch.backend.model.Reservation;
import de.cinetastisch.backend.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping
    public Reservation addReservation(ReservationRequestDto request){
        return reservationService.addReservation(request);
    }
}
