package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.SeatRequestDto;
import de.cinetastisch.backend.dto.response.SeatResponseDto;
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
    public ResponseEntity<List<SeatResponseDto>> getAll(@RequestParam(value = "roomId", required = false) Long roomId,
                                                        @RequestParam(value = "category", required = false) String category){
        return ResponseEntity.ok(seatService.getAllSeats(roomId, category));
    }

    @Operation(
            tags = {"Seats"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDto> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(seatService.getSeat(id));
    }

    @Operation(
            tags = {"Seats"}
    )
    @PostMapping
    public ResponseEntity<SeatResponseDto> addOne(@RequestBody SeatRequestDto seat){
        return new ResponseEntity<>(seatService.addSeat(seat), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Seats"}
    )
    @PutMapping
    public ResponseEntity<List<SeatResponseDto>> replaceMultiple(@RequestBody List<SeatRequestDto> request){
        return new ResponseEntity<>(seatService.replaceSeats(request), HttpStatus.CREATED);
    }


    @Operation(
            tags = {"Seats"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDto> replaceOne(@PathVariable("id") Long id,
                                                      @RequestBody SeatRequestDto seat){
        return ResponseEntity.ok(seatService.replaceSeat(id, seat));
    }
}
