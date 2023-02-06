package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, UserMapper.class, TicketMapper.class, ReservationMapper.class},
        imports = Collectors.class)
public interface OrderMapper {

    @Mapping(target = "faresSelected", expression = "java(order.getTickets().stream().collect(Collectors.groupingBy(o -> o.getSelectedFare().getName(), Collectors.counting())))")
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "id", expression = "java(order.getId())")
    OrderResponseDto entityToDto(Order order);
    List<OrderResponseDto> entityToDto(Iterable<Order> order);

//    @Named("generateFareSelection")
//    default Map<String, Long> generateFareSelection(Order order){
//        return order.getTickets()
//                                 .stream()
//                                 .collect(Collectors.groupingBy(o -> o.getSelectedFare().getName(), Collectors.counting()));
//    }
}
