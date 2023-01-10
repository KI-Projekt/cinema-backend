package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.ScreeningRequestDto;
import de.cinetastisch.backend.dto.ScreeningResponseDto;
import de.cinetastisch.backend.model.Screening;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ReferenceMapper.class, RoomMapper.class, MovieMapper.class})
public interface ScreeningMapper {

    @Mapping(target = "room", source="roomId")
    @Mapping(target = "movie", source="movieId")
    @Mapping(target = "id", ignore = true)
    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);
    List<Screening> dtoToEntity(Iterable<ScreeningRequestDto> screeningRequestDtos);

    @Mapping(target = "id", expression = "java(screening.getId())")
    @Mapping(target = "movieId", expression = "java(screening.getMovie().getId())")
    @Mapping(target = "duration", ignore = true, defaultExpression = "java(ChronoUnit.MINUTES.between(screening.startDateTime, screening.endDateTime))")
    ScreeningResponseDto entityToDto(Screening screening);
    List<ScreeningResponseDto> entityToDto(Iterable<Screening> screenings);
}
