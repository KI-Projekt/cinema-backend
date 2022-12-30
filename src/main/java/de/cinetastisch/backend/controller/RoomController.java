package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.enumeration.RoomAudioExperience;
import de.cinetastisch.backend.enumeration.RoomScreenExperience;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.pojo.RoomInfo;
import de.cinetastisch.backend.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAll(){
        return roomService.getAllRooms();
    }

    @PostMapping
    public ResponseEntity<Room> add(@Valid @RequestBody RoomInfo roomInfo){
        System.out.println(roomInfo);
        return new ResponseEntity<>(roomService.addRoom(
                roomInfo.name(),
                RoomScreenExperience.valueOf(roomInfo.roomScreenExperience()),
                RoomAudioExperience.valueOf(roomInfo.roomAudioExperience())
        ), HttpStatus.CREATED);
    }
}
