package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.UserRequestDto;
import de.cinetastisch.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class})
public interface UserMapper {

    User dtoToEntity(UserRequestDto userRequestDto);
}
