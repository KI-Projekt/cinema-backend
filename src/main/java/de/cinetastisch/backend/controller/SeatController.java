package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.SeatDto;
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
    public ResponseEntity<List<SeatDto>> getAll(@RequestParam(value = "roomId", required = false) Long roomId,
                                             @RequestParam(value = "category", required = false) String category){
        return ResponseEntity.ok(seatService.getAllSeats(roomId, category));
    }

    @Operation(
            tags = {"Seats"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(seatService.getSeat(id));
    }

    @Operation(
            tags = {"Seats"}
    )
    @PostMapping
    public ResponseEntity<SeatDto> addOne(@RequestBody SeatDto seat){
        return new ResponseEntity<>(seatService.addSeat(seat), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Seats"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<SeatDto> getOne(@PathVariable("id") Long id,
                                          @RequestBody SeatDto seat){
        return ResponseEntity.ok(seatService.replaceSeat(id, seat));
    }
}
