package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.SeatDto;
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

    @Mapping(target = "row", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "column", ignore = true)
    @Mapping(target = "category", ignore = true)
    Seat toEntity(Long id);



    @Mapping(target = "room", source = "seatDto")
    @Mapping(target = "id", ignore = true)
    Seat dtoToEntity(SeatDto seatDto);
    List<Seat> dtoToEntity(Iterable<SeatDto> seatDto);


    @Mapping(target = "roomId", source = "seat.room.id")
    SeatDto EntityToDto(Seat seat);
    List<SeatDto> EntityToDto(Iterable<Seat> seat);
}
