package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.dto.response.UserResponseDto;
import de.cinetastisch.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class})
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    User dtoToEntity(UserRequestDto userRequestDto);
    List<User> dtoToEntity(Iterable<UserRequestDto> userRequestDto);


    @Mapping(target = "id", expression = "java(user.getId())")
    UserResponseDto entityToDto(User user);
    List<UserResponseDto> entityToDto(Iterable<User> user);

}
