package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.SeatDto;
import de.cinetastisch.backend.dto.SeatResponseDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.SeatMapper;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.repository.RoomRepository;
import de.cinetastisch.backend.repository.SeatRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    private final SeatMapper mapper;

    @Operation(
            tags = {"Seats"}
    )
    public List<SeatResponseDto> getAllSeats(Long roomId, String category){
        if(roomId != null && category != null){
            Room room = roomRepository.getReferenceById(roomId);
            return mapper.entityToDto(seatRepository.findAllByRoomAndCategory(room, SeatCategory.valueOf(category)));
        } else if (roomId != null){
            Room room = roomRepository.getReferenceById(roomId);
            return mapper.entityToDto(seatRepository.findAllByRoom(room));
        } else if (category != null){
            return mapper.entityToDto(seatRepository.findAllByCategory(SeatCategory.valueOf(category)));
        }
        return mapper.entityToDto(seatRepository.findAll());
    }

    @Operation(
            tags = {"Seats"}
    )
    public SeatResponseDto getSeat(Long id){
        return mapper.entityToDto(seatRepository.getReferenceById(id));
    }

    @Operation(
            tags = {"Seats"}
    )
    public SeatResponseDto addSeat(SeatDto seatRequest){
        Seat newSeat = mapper.dtoToEntity(seatRequest);
        return mapper.entityToDto(saveSeat(newSeat));
    }

    @Operation(
            tags = {"Seats"}
    )
    public SeatResponseDto replaceSeat(Long id, SeatDto seatRequest){
        Seat oldSeat = seatRepository.getReferenceById(id);
        Seat newSeat = mapper.dtoToEntity(seatRequest);
        newSeat.setId(oldSeat.getId());

        return mapper.entityToDto(saveSeat(newSeat));
    }

    @Operation(
            tags = {"Seats"}
    )
    public void deleteSeat(Long id){
        Seat seat = seatRepository.getReferenceById(id);
        seatRepository.delete(seat);
    }

    @Operation(
            tags = {"Seats"}
    )
    public Seat saveSeat(Seat seat){
        if(seatRepository.existsByRowAndColumn(seat.getRow(), seat.getColumn())){
            throw new ResourceAlreadyExistsException("There is already a seat in this position");
        }
        return seatRepository.save(seat);
    }

}
