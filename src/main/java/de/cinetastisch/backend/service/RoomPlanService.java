package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.response.ScreeningSeatDto;
import de.cinetastisch.backend.dto.response.ScreeningSeatRowDto;
import de.cinetastisch.backend.dto.response.SeatResponseDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    @Named("generateSeatingPlan")
    public List<ScreeningSeatRowDto> getSeatingPlan(Screening screening){
        Room room = screening.getRoom();
        List<Seat> seats = room.getSeats();

        orderRepository.findAllByStatus(OrderStatus.IN_PROGRESS);
        ticketRepository.findAll();
        List<Ticket> tickets = ticketRepository.findAllByScreening(screening);
        tickets = tickets.stream()
                         .filter(ticket -> ticket.getOrder().getStatus() != OrderStatus.CANCELLED)
                         .filter(ticket -> ticket.getOrder().getStatus() != OrderStatus.REFUNDED)
                         .toList();

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
