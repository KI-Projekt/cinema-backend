package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.OrderRequestDto;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class OrderMapper {

    @Autowired
    protected UserService userService;

    @Mapping(target = "user", expression = "java(userService.getUser(orderRequestDto.userId()))")
    @Mapping(target = "orderStatus", defaultValue = "IN_PROGRESS", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Order dtoToEntity(OrderRequestDto orderRequestDto);
}
