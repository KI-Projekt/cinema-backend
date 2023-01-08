package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.ReservationResponseDto;
import de.cinetastisch.backend.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ReservationMapper {

    @Mapping(target = "seatRow", source = "seat.row")
    @Mapping(target = "seatColumn", source = "seat.column")
    @Mapping(target = "screeningId", source = "screening.id")
    ReservationResponseDto entityToDto(Reservation reservation);

    List<ReservationResponseDto> entityToDto(Iterable<Reservation> reservations);
}
