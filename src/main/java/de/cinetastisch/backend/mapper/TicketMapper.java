package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.TicketResponseDto;
import de.cinetastisch.backend.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, OrderMapper.class, ScreeningMapper.class, SeatMapper.class})
public interface TicketMapper {

    @Mapping(target = "orderId", expression = "java(ticket.getOrder().getId())")
    @Mapping(target = "id", expression = "java(ticket.getId())")
    TicketResponseDto entityToDto(Ticket ticket);
    List<TicketResponseDto> entityToDto(Iterable<Ticket> tickets);
}
