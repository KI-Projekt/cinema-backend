package de.cinetastisch.backend.mapper;

import de.cinetastisch.backend.dto.ScreeningRequestDto;
import de.cinetastisch.backend.dto.ScreeningResponseDto;
import de.cinetastisch.backend.model.Screening;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ReferenceMapper.class, RoomMapper.class, MovieMapper.class})
public interface ScreeningMapper {

    @Mapping(target = "startDateTime", source="startDateTime", defaultExpression = "java(LocalDateTime.now())")
    @Mapping(target = "status", source = "status", defaultValue = "TICKET_SALE_OPEN")
    @Mapping(target = "room", source="roomId")
    @Mapping(target = "movie", source="movieId")
    @Mapping(target = "id", ignore = true)
    Screening dtoToEntity(ScreeningRequestDto request);
    List<Screening> dtoToEntity(Iterable<ScreeningRequestDto> requests);

    @Mapping(target = "id", expression = "java(screening.getId())")
    @Mapping(target = "movieId", expression = "java(screening.getMovie().getId())")
    @Mapping(target = "duration", expression= "java(java.time.temporal.ChronoUnit.MINUTES.between(screening.getStartDateTime(), screening.getEndDateTime()))")
    ScreeningResponseDto entityToDto(Screening screening);
    List<ScreeningResponseDto> entityToDto(Iterable<Screening> screenings);
}
