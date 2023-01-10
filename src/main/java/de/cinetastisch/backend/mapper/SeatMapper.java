package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.SeatDto;
import de.cinetastisch.backend.dto.SeatResponseDto;
import de.cinetastisch.backend.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, RoomMapper.class})
public interface SeatMapper {

    @Mapping(target = "room", source = "seatDto.roomId")
    @Mapping(target = "id", ignore = true)
    Seat dtoToEntity(SeatDto seatDto);
    List<Seat> dtoToEntity(Iterable<SeatDto> seatDto);



//    @Mapping(target = "roomId", expression = "java(seat.getRoom().getId())")
    @Mapping(target = "id", expression = "java(seat.getId())")
    SeatResponseDto entityToDto(Seat seat);
    List<SeatResponseDto> entityToDto(Iterable<Seat> seat);

}
