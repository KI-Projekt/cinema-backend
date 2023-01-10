package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.ScreeningRequestDto;
import de.cinetastisch.backend.dto.ScreeningResponseDto;
import de.cinetastisch.backend.model.Screening;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ReferenceMapper.class, RoomMapper.class, MovieMapper.class})
public interface ScreeningMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "startDateTime", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "endDateTime", ignore = true)
    Screening toEntity(Long id);

    @Mapping(target = "room", source = "roomId")
    @Mapping(target = "movie", source = "movieId")
    @Mapping(target = "id", ignore = true)
    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);

    ScreeningResponseDto entityToDto(Screening screening);
    List<ScreeningResponseDto> entityToDto(Iterable<Screening> screenings);
}
