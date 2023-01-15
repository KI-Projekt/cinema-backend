package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.RoomRequestDto;
import de.cinetastisch.backend.dto.RoomResponseDto;
import de.cinetastisch.backend.dto.SeatRowsDto;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, SeatMapper.class})
public interface RoomMapper {

    @Mapping(target = "seats", ignore = true)
    @Mapping(target = "id", ignore = true)
    Room dtoToEntity(RoomRequestDto roomRequestDto);
    List<Room> dtoToEntity(Iterable<RoomRequestDto> roomRequestDtos);

    List<SeatRowsDto> seatsToRowsDto(List<Seat> seatis);

    @Mapping(target = "id", expression = "java(room.getId())")
    RoomResponseDto entityToDto(Room room);
    List<RoomResponseDto> entityToDto(Iterable<Room> rooms);
}
