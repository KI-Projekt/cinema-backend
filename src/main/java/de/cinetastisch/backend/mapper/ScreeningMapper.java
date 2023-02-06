package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.service.RoomPlanService;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ReferenceMapper.class, RoomMapper.class, MovieMapper.class, RoomPlanService.class})
public interface ScreeningMapper {


    @Mapping(target = "threeD", source = "isThreeD", defaultValue = "false")
    @Mapping(target = "dolbyAtmos", source = "isDolbyAtmos", defaultValue = "false")
    @Mapping(target = "startDateTime", source="startDateTime", defaultExpression = "java(LocalDateTime.now())")
    @Mapping(target = "status", source = "status", defaultValue = "TICKET_SALE_OPEN")
    @Mapping(target = "room", source="roomId")
    @Mapping(target = "movie", source="movieId")
    @Mapping(target = "id", ignore = true)
    Screening dtoToEntity(ScreeningRequestDto request);
    List<Screening> dtoToEntity(Iterable<ScreeningRequestDto> requests);

    @Mapping(target = "isThreeD", source = "threeD")
    @Mapping(target = "isDolbyAtmos", source = "dolbyAtmos")
    @Mapping(target = "seatingPlan", source = "screening", qualifiedByName = "generateSeatingPlan")
    @Mapping(target = "id", expression = "java(screening.getId())")
    @Mapping(target = "duration", expression= "java(java.time.temporal.ChronoUnit.MINUTES.between(screening.getStartDateTime(), screening.getEndDateTime()))")
    ScreeningFullResponseDto entityToDto(Screening screening);
    List<ScreeningFullResponseDto> entityToDto(Iterable<Screening> screenings);

    @Mapping(target = "duration", expression = "java(screeningResponseDto.duration())")
    @Mapping(target = "id", expression = "java(screeningResponseDto.id())")
    ScreeningResponseDto trimDto(ScreeningFullResponseDto screeningResponseDto);
    List<ScreeningResponseDto> trimDto(Iterable<ScreeningFullResponseDto> screeningResponseDto);

////    @Named("generateSeatingPlan")
//    default List<ScreeningSeatRowDto> getSeatingPlan(Screening screening){
//        Room room = screening.getRoom();
//        List<Seat> seats = room.getSeats();
//
//        List<ScreeningSeatRowDto> roomPlan = new ArrayList<>();
//
//        Map<Integer, List<Seat>> rowList = seats.stream()
//                                                .collect(Collectors.groupingBy(Seat::getRow));
//
////        List<Ticket> screeningTickets = screening.getOrders()
////                                                 .stream()
////                                                 .flatMap(o -> o.getTickets().stream())
////                                                 .toList();
////        List<Reservation> screeningReservations = screening.getOrders()
////                                                           .stream()
////                                                           .flatMap(o -> o.getReservations().stream())
////                                                           .toList();
//
//
//        for(List<Seat> row : rowList.values()){
//            List<ScreeningSeatDto> screeningSeats = new ArrayList<>();
//
//            for(Seat seat : row){
//
//                boolean reserved =
//                        screening.getTickets()
//                                 .stream()
//                                 .filter(Objects::nonNull)
//                                 .anyMatch(ticket -> ticket.getSeat() == seat);
//
////
////                        screeningReservations
////                                 .stream()
////                                 .filter(Objects::nonNull)
////                                 .anyMatch(reservation -> reservation.getSeat() == seat);
//
//
//                screeningSeats.add(
//                        new ScreeningSeatDto(
//                                new SeatResponseDto(
//                                        seat.getId(),
//                                        seat.getCategory(),
//                                        seat.getRow(),
//                                        seat.getColumn()),
//                                reserved)
//                );
//            }
//            roomPlan.add(new ScreeningSeatRowDto("Reihe" + screeningSeats.get(0).seat().row(), screeningSeats));
//        }
//
//        return roomPlan;
//    }
}
