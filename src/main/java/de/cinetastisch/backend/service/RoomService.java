package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.*;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.exception.NoResourcesException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.RoomMapper;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final RoomMapper mapper;

    public List<RoomSlimResponseDto> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            throw new NoResourcesException("Empty");
        }
        return mapper.dtoToSlimDto(mapper.entityToDto(rooms));
    }

    public RoomResponseDto getRoom(Long id){
        Room room = roomRepository.getReferenceById(id);
        return mapper.entityToDto(room);
    }

    @Transactional
    public RoomResponseDto addRoom(RoomRequestDto request){
        Room newRoom = roomRepository.save(mapper.dtoToEntity(request));
        if(request.numberOfRows() != null && request.numberOfColumns() != null){
            if(request.numberOfRows() < 0 || request.numberOfColumns() < 0 ){
                throw new IllegalArgumentException("Number must be positive");
            }

            List<Seat> seats = new ArrayList<>();

            for(int r = 1; r <= request.numberOfRows(); r++){
                for(int c = 1; c <= request.numberOfColumns(); c++){
                    seats.add(new Seat(newRoom, r, c, SeatCategory.NORMAL));
                }
            }

            newRoom.setSeats(seats);
            roomRepository.save(newRoom);
        }
        return mapper.entityToDto(newRoom);
    }

    @Transactional
    public RoomResponseDto replaceRoom(Long id, RoomPutRequestDto request) {
        if(id != request.id()){
            throw new IllegalArgumentException("Different ids given");
        }

        Room oldRoom = roomRepository.getReferenceById(id);
        if(request.name() != null){
            oldRoom.setName(request.name());
        }
        if(request.hasThreeD() != null){
            oldRoom.setHasThreeD(Boolean.getBoolean(request.hasThreeD()));
        }
        if(request.hasDolbyAtmos() != null){
            oldRoom.setHasThreeD(Boolean.getBoolean(request.hasDolbyAtmos()));
        }

        if(request.seats().size() > 0){
            List<Seat> changedSeats = new ArrayList<>();
            for(SeatPutRequestDto seatRequest : request.seats()){
                Seat seat = seatRepository.getReferenceById(seatRequest.id());
                seat.setCategory(SeatCategory.valueOf(seatRequest.seatCategory()));
                changedSeats.add(seat);
            }
            seatRepository.saveAll(changedSeats);
        }
        return mapper.entityToDto(roomRepository.save(oldRoom));
    }

    public void deleteRoom(Long id){
        if(!roomRepository.existsById(id)){
            throw new ResourceNotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }

}
