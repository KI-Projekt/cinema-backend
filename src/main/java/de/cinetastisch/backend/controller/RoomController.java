package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.RoomPutRequestDto;
import de.cinetastisch.backend.dto.request.RoomRequestDto;
import de.cinetastisch.backend.dto.response.RoomResponseDto;
import de.cinetastisch.backend.dto.response.RoomSlimResponseDto;
import de.cinetastisch.backend.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Operation(
            tags = {"Rooms"}
    )
    @GetMapping
    public ResponseEntity<List<RoomSlimResponseDto>> getAll(){
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getOne(@Valid @PathVariable Long id){
        return new ResponseEntity<>(roomService.getRoom(id), HttpStatus.OK);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @PostMapping
    public ResponseEntity<RoomResponseDto> add(@Valid @RequestBody RoomRequestDto roomRequestDto){
        return new ResponseEntity<>(roomService.addRoom(roomRequestDto), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> replaceRoom(@Valid @PathVariable Long id,
                                                      @Valid @RequestBody RoomPutRequestDto request){
        return new ResponseEntity<>(roomService.replaceRoom(id, request), HttpStatus.OK);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@Valid @PathVariable Long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
