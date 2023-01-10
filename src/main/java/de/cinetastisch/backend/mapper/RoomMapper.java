package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.RoomRequestDto;
import de.cinetastisch.backend.dto.RoomResponseDto;
import de.cinetastisch.backend.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class})
public interface RoomMapper {

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hasThreeD", ignore = true)
    @Mapping(target = "hasDolbyAtmos", ignore = true)
    Room toEntity(Long id);

    @Mapping(target = "id", ignore = true)
    Room dtoToEntity(RoomRequestDto roomRequestDto);
    List<Room> dtoToEntity(Iterable<RoomRequestDto> roomRequestDtos);

    RoomResponseDto EntityToDto(Room room);
    List<RoomResponseDto> EntityToDto(Iterable<Room> rooms);
}
