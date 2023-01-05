package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.SeatRequestDto;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.service.RoomService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class SeatMapper {

    @Autowired
    protected RoomService roomService;


    @Mapping(target = "room", expression = "java(roomService.getRoom(seatRequestDto.roomId()))")
    @Mapping(target = "id", ignore = true)
    public abstract Seat dtoToEntity(SeatRequestDto seatRequestDto);
}
