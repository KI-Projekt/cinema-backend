package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.SeatRequestDto;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.SeatMapper;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.repository.SeatRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final RoomService roomService;
    private final TicketService ticketService;
    private final SeatMapper mapper;

    public SeatService(SeatRepository seatRepository, RoomService roomService,
                       TicketService ticketService, SeatMapper mapper) {
        this.seatRepository = seatRepository;
        this.roomService = roomService;
        this.ticketService = ticketService;
        this.mapper = mapper;
    }

    @Operation(
            tags = {"Seats"}
    )
    public List<Seat> getAllSeats(Long roomId, String category){
        if(roomId != null && category != null){
            Room room = roomService.getRoom(roomId);
            return seatRepository.findAllByRoomAndCategory(room, SeatCategory.valueOf(category));
        } else if (roomId != null){
            Room room = roomService.getRoom(roomId);
            return seatRepository.findAllByRoom(room);
        } else if (category != null){
            return seatRepository.findAllByCategory(SeatCategory.valueOf(category));
        }
        return seatRepository.findAll();
    }

    @Operation(
            tags = {"Seats"}
    )
    public Seat getSeat(Long id){
        return seatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
    }

    @Operation(
            tags = {"Seats"}
    )
    public Seat addSeat(SeatRequestDto seatRequest){
        Seat newSeat = mapper.dtoToEntity(seatRequest);
        return saveSeat(newSeat);
    }

    @Operation(
            tags = {"Seats"}
    )
    public Seat replaceSeat(Long id, SeatRequestDto seatRequest){
        Seat oldSeat = getSeat(id);
        Seat newSeat = mapper.dtoToEntity(seatRequest);
        newSeat.setId(oldSeat.getId());

        return saveSeat(newSeat);
    }

    @Operation(
            tags = {"Seats"}
    )
    public void deleteSeat(Long id){
        Seat seat = getSeat(id);
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
