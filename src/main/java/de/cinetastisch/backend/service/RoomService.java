package de.cinetastisch.backend.service;

import de.cinetastisch.backend.enumeration.RoomAudioExperience;
import de.cinetastisch.backend.enumeration.RoomScreenExperience;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoom(Long id){
        return roomRepository.findById(id).get();
    }

    public Room addRoom(String name, RoomScreenExperience roomScreenExperience, RoomAudioExperience roomAudioExperience){
        Room room = roomRepository.save(new Room(name, roomScreenExperience, roomAudioExperience));

        if (Objects.equals(name, null)){
            name = "Raum Nr. " + room.getId();
            room = roomRepository.save(new Room(name, roomScreenExperience, roomAudioExperience));
        }

        return room;
    }
}
