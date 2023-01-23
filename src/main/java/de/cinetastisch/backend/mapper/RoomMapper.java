package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.*;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, SeatMapper.class})
public interface RoomMapper {

    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "id", ignore = true)
    Room dtoToEntity(RoomRequestDto roomRequestDto);
    List<Room> dtoToEntity(Iterable<RoomRequestDto> roomRequestDtos);



    @Mapping(target = "rows", source = "room", qualifiedByName = "generateRoomPlan")
    @Mapping(target = "id", expression = "java(room.getId())")
    RoomResponseDto entityToDto(Room room);
    List<RoomResponseDto> entityToDto(Iterable<Room> rooms);

    @Mapping(target = "id", expression = "java(room.id())")
    RoomSlimResponseDto dtoToSlimDto(RoomResponseDto room);
    List<RoomSlimResponseDto> dtoToSlimDto(Iterable<RoomResponseDto> room);

    @Named("generateRoomPlan")
    default List<RoomSeatRowDto> generateRoomPlan(Room room){
        Map<Integer, List<Seat>> rowList = room.getSeats()
                                               .stream()
                                               .collect(Collectors.groupingBy(Seat::getRow));
        List<RoomSeatRowDto> result = new ArrayList<>();

        for(List<Seat> row : rowList.values()){
            List<SeatResponseDto> seats = new ArrayList<>();
            for(Seat s : row){
                seats.add(new SeatResponseDto(s.getId(), s.getCategory(), s.getRow(), s.getColumn()));
            }
            result.add(new RoomSeatRowDto("Reihe " + seats.get(0).row(), seats));
        }
        return result;
    }
}
