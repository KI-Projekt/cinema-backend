package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Room addRoom(Boolean isThreeD, Boolean isDolbyAtmos){
        Room newRoom = new Room(isThreeD, isDolbyAtmos);
        return roomRepository.save(newRoom);
    }
}
