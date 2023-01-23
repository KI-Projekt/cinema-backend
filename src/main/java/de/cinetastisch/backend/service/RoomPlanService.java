package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.ScreeningSeatDto;
import de.cinetastisch.backend.dto.ScreeningSeatRowDto;
import de.cinetastisch.backend.dto.SeatResponseDto;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomPlanService {

    private final TicketRepository ticketRepository;

    @Named("generateSeatingPlan")
    public List<ScreeningSeatRowDto> getSeatingPlan(Screening screening){
        Room room = screening.getRoom();
        List<Seat> seats = room.getSeats();
        List<Ticket> tickets = ticketRepository.findAllByScreening(screening);

        List<ScreeningSeatRowDto> roomPlan = new ArrayList<>();

        Map<Integer, List<Seat>> rowList = seats.stream()
                                                .collect(Collectors.groupingBy(Seat::getRow));

        for(List<Seat> row : rowList.values()){
            List<ScreeningSeatDto> screeningSeats = new ArrayList<>();

            for(Seat seat : row){

                boolean reserved =
                        tickets.stream()
                               .filter(Objects::nonNull)
                               .anyMatch(ticket -> ticket.getSeat() == seat);

                screeningSeats.add(
                        new ScreeningSeatDto(
                                new SeatResponseDto(
                                        seat.getId(),
                                        seat.getCategory(),
                                        seat.getRow(),
                                        seat.getColumn()),
                                reserved)
                );
            }
            roomPlan.add(new ScreeningSeatRowDto("Reihe" + screeningSeats.get(0).seat().row(), screeningSeats));
        }

        return roomPlan;
    }
}
