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

    @Mapping(target = "zip", ignore = true)
    @Mapping(target = "street", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "houseNumber", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "birthday", ignore = true)
    User toEntity(Long id);

    User dtoToEntity(UserRequestDto userRequestDto);
}
