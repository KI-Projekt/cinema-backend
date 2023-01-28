package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, UserMapper.class, TicketMapper.class, ReservationMapper.class})
public interface OrderMapper {

//    @Mapping(target = "expiresAt", expression = "java(order.getStatus() == de.cinetastisch.backend.enumeration.OrderStatus.IN_PROGRESS ? order.getCreatedAt().plusMinutes(1) : null)")
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "id", expression = "java(order.getId())")
    OrderResponseDto entityToDto(Order order);
    List<OrderResponseDto> entityToDto(Iterable<Order> order);
}
