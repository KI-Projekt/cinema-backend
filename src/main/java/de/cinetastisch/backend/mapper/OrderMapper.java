package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.OrderRequestDto;
import de.cinetastisch.backend.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    Order toEntity(Long id);

    @Mapping(target = "total", ignore = true)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "orderStatus", defaultValue = "IN_PROGRESS", ignore = true)
    @Mapping(target = "id", ignore = true)
    Order dtoToEntity(OrderRequestDto orderRequestDto);
}
