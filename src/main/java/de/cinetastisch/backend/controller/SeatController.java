package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.SeatRequestDto;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Operation(
            tags = {"Seats"}
    )
    @GetMapping
    public ResponseEntity<List<Seat>> getAll(@RequestParam(value = "roomId", required = false) Long roomId,
                                             @RequestParam(value = "category", required = false) String category){
        return ResponseEntity.ok(seatService.getAllSeats(roomId, category));
    }

    @Operation(
            tags = {"Seats"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(seatService.getSeat(id));
    }

    @Operation(
            tags = {"Seats"}
    )
    @PostMapping
    public ResponseEntity<Seat> addOne(@RequestBody SeatRequestDto seat){
        return new ResponseEntity<>(seatService.addSeat(seat), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Seats"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<Seat> getOne(@PathVariable("id") Long id,
                                       @RequestBody SeatRequestDto seat){
        return ResponseEntity.ok(seatService.replaceSeat(id, seat));
    }
}
