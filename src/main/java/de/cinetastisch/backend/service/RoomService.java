package de.cinetastisch.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cinetastisch.backend.exception.NoResourcesException;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    public RoomService(RoomRepository roomRepository, ObjectMapper objectMapper) {
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
    }

    public List<Room> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            throw new NoResourcesException("Empty");
        }
        return rooms;
    }

    public Room getRoom(Long id){
        return roomRepository.findById(id)
                             .orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
    }

    public Room addRoom(Room room){
        if(roomRepository.existsByNameIgnoreCase(room.getName())){
            throw new ResourceAlreadyExistsException("Name is already taken");
        }
        return roomRepository.save(room);
    }

    public Room replaceRoom(Long id, Room newRoom) {
        newRoom.setId(id);
        Room oldRoom = roomRepository.findById(id)
                                     .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + id + " not found"));
        if(roomRepository.existsByNameIgnoreCase(newRoom.getName()) && !oldRoom.getName().equals(newRoom.getName())){
            throw new ResourceAlreadyExistsException("Name is already taken by another room.");
        }
        return roomRepository.save(newRoom);
    }

    public void deleteRoom(Long id){
        if(!roomRepository.existsById(id)){
            throw new ResourceNotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }

}
