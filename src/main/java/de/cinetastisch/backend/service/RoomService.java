package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.RoomRequestDto;
import de.cinetastisch.backend.dto.RoomResponseDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.exception.NoResourcesException;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.RoomMapper;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final RoomMapper mapper;

    public List<RoomResponseDto> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            throw new NoResourcesException("Empty");
        }
        return mapper.entityToDto(rooms);
    }

    public RoomResponseDto getRoom(Long id){
        return mapper.entityToDto(roomRepository.getReferenceById(id));
    }

    public Room getRoomEntity(Long id){
        return roomRepository.getReferenceById(id);
    }

    @Transactional
    public RoomResponseDto addRoom(RoomRequestDto request, Integer rows, Integer columns){
        Room newRoom = mapper.dtoToEntity(request);
        if(roomRepository.existsByNameIgnoreCase(newRoom.getName())){
            throw new ResourceAlreadyExistsException("Name is already taken");
        }

        roomRepository.save(newRoom);

        System.out.println("Null as id? " + newRoom);

        if(rows != null || columns != null){
            if(rows < 0 || columns < 0 ){
                throw new IllegalArgumentException("Number should be positive");
            }

            for(int r = 1; r <= rows; r++){
                for(int c = 1; c <= columns; c++){
                    seatRepository.save(new Seat(newRoom, r, c, SeatCategory.NORMAL));
                }
            }
        }
        return mapper.entityToDto(newRoom);
    }

    public RoomResponseDto replaceRoom(Long id, RoomRequestDto request) {
        Room newRoom = mapper.dtoToEntity(request);
        newRoom.setId(id);
        Room oldRoom = roomRepository.getReferenceById(id);
        if(roomRepository.existsByNameIgnoreCase(newRoom.getName()) && !oldRoom.getName().equals(newRoom.getName())){
            throw new ResourceAlreadyExistsException("Name is already taken by another room.");
        }
        return mapper.entityToDto(roomRepository.save(newRoom));
    }

    public void deleteRoom(Long id){
        if(!roomRepository.existsById(id)){
            throw new ResourceNotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }

}
