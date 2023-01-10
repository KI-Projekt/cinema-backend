package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.RoomRequestDto;
import de.cinetastisch.backend.dto.RoomResponseDto;
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
    public ResponseEntity<List<RoomResponseDto>> getAll(){
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
        return new ResponseEntity<>(roomService.addRoom(roomRequestDto, roomRequestDto.numberOfRows(), roomRequestDto.numberOfColumns()), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> replaceRoom(@Valid @PathVariable Long id,
                                                      @Valid @RequestBody RoomRequestDto roomRequestDto){
        return new ResponseEntity<>(roomService.replaceRoom(id, roomRequestDto), HttpStatus.OK);
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
