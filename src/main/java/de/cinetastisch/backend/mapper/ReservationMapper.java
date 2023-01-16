package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.ReservationRequestDto;
import de.cinetastisch.backend.dto.ReservationResponseDto;
import de.cinetastisch.backend.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, OrderMapper.class, UserMapper.class, SeatMapper.class})
public interface ReservationMapper {

    @Mapping(target = "category", ignore = true, defaultValue = "ADULT")
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "seat", source = "seatId")
    @Mapping(target = "screening", source = "screeningId")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    Reservation dtoToEntity(ReservationRequestDto reservationRequestDto);
    List<Reservation> dtoToEntity(Iterable<ReservationRequestDto> reservationRequestDtos);



    @Mapping(target = "orderId", expression = "java(reservation.getOrder().getId())")
    @Mapping(target = "screeningId", expression = "java(reservation.getScreening().getId())")
    @Mapping(target  = "id", expression = "java(reservation.getId())")
    ReservationResponseDto entityToDto(Reservation reservation);
    List<ReservationResponseDto> entityToDto(Iterable<Reservation> reservations);
}
