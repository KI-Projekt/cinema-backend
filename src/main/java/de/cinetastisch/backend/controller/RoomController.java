package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.RoomDto;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService, ModelMapper modelMapper) {
        this.roomService = roomService;
    }

    @Operation(
            tags = {"Rooms"}
    )
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAll(){
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDto> roomDtos = rooms.stream()
                                      .map(this::convertToDto)
                                      .toList();
        return new ResponseEntity<>(roomDtos, HttpStatus.OK);
//        return posts.stream()
//          .map(this::convertToDto)
//          .collect(Collectors.toList());
    }

    @Operation(
            tags = {"Rooms"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getOne(@Valid @PathVariable Long id){
        return new ResponseEntity<>(convertToDto(roomService.getRoom(id)), HttpStatus.OK);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @PostMapping
    public ResponseEntity<RoomDto> add(@Valid @RequestBody RoomDto roomDto){
        if (roomDto.id() != null){
            throw new IllegalArgumentException("IDs are auto generated, please omit them from your request body");
        }
        return new ResponseEntity<>(convertToDto(roomService.addRoom(convertToEntity(roomDto))), HttpStatus.CREATED);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> replaceRoom(@Valid @PathVariable Long id,
                                               @Valid @RequestBody RoomDto roomDto){
        return new ResponseEntity<>(convertToDto(roomService.replaceRoom(id, convertToEntity(roomDto))), HttpStatus.OK);
    }

    @Operation(
            tags = {"Rooms"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@Valid @PathVariable Long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public Room convertToEntity(RoomDto roomDto){
        return Room.builder()
                   .name(roomDto.name())
                   .hasThreeD(Boolean.valueOf(roomDto.hasThreeD()))
                   .hasDolbyAtmos(Boolean.valueOf(roomDto.hasDolbyAtmos()))
                   .build();
    }

    public RoomDto convertToDto(Room room){
        return RoomDto.builder()
                      .id(room.getId())
                      .name(room.getName())
                      .hasThreeD(room.getHasThreeD().toString())
                      .hasDolbyAtmos(room.getHasDolbyAtmos().toString())
                      .build();
    }
}
