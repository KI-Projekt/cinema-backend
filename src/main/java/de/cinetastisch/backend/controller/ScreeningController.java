package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.service.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @Operation(
            tags = {"Screenings"},
            parameters = {
                    @Parameter(name = "sort"),
                    @Parameter(name = "status", example = "TICKET_SALE_CLOSED")
            }

    )
    @Transactional
    @GetMapping
    public ResponseEntity<List<ScreeningResponseDto>> getAll(
            @Join(path= "movie", alias = "m")
            @Join(path= "room", alias = "r")
            @And({
                @Spec(path = "status", params = "status", paramSeparator = ',', spec = In.class, defaultVal = "TICKET_SALE_OPEN"),
                @Spec(path = "startDateTime", params = "startDateTime", spec = GreaterThanOrEqual.class),
                @Spec(path = "endDateTime", params = "endDateTime", spec = LessThanOrEqual.class),
                @Spec(path = "isThreeD", params = "3D", spec = Equal.class),
                @Spec(path = "isDolbyAtmos", params = "dolby", spec = Equal.class),
                @Spec(path = "m.id", params = "movieId", spec = Equal.class),
                @Spec(path = "m.title", params = "movieTitle", spec = LikeIgnoreCase.class),
                @Spec(path = "m.releaseYear", params = "releaseYear", spec = Equal.class),
                @Spec(path = "m.rated", params = "rated", paramSeparator = ',', spec = LessThanOrEqual.class),
                @Spec(path = "m.runtime", params = "runtime", spec = Equal.class),
                @Spec(path = "m.genre", params = "genre", paramSeparator = ',', spec = In.class),
                @Spec(path = "m.director", params = "director", spec = LikeIgnoreCase.class),
                @Spec(path = "m.writer", params = "writer", spec = LikeIgnoreCase.class),
                @Spec(path = "m.actors", params = "actor", spec = LikeIgnoreCase.class),
                @Spec(path = "m.actors", params = "actors", paramSeparator = ',', spec = In.class),
                @Spec(path = "m.plot", params = "plot", spec = LikeIgnoreCase.class),
                @Spec(path = "m.imdbId", params = "imdbId", spec = Equal.class),
                @Spec(path = "m.imdbRating", params = "imdbRating", spec = GreaterThanOrEqual.class),
                @Spec(path = "r.id", params = "roomId", spec = Equal.class),
                @Spec(path = "r.name", params = "roomName", spec = LikeIgnoreCase.class)
            }) Specification<Screening> spec,
            @ParameterObject @PageableDefault(sort = "startDateTime") Pageable page){
        return ResponseEntity.ok(screeningService.getAllScreenings(spec, page));
    }

    @Operation(
            tags = {"Screenings"},
            summary = "Returns screening with a seating plan of reservations"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ScreeningFullResponseDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(screeningService.getScreening(id));
    }

    @Operation(
            tags = {"Screenings"},
            description = "EndDateTime is optional and can be automatically calculated if ommited",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ScreeningRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @PostMapping
    public ResponseEntity<ScreeningFullResponseDto> add(@RequestBody ScreeningRequestDto screeningRequestDto){
        return new ResponseEntity<>(screeningService.addScreening(screeningRequestDto), HttpStatus.CREATED);
    }


    @Operation(
            tags = {"Screenings"}
    )
    @PutMapping("{id}")
    public ResponseEntity<ScreeningFullResponseDto> replaceOne(@Valid @RequestBody ScreeningRequestDto screeningDto,
                                                @Valid @PathVariable("id") Long id){
        return new ResponseEntity<>(screeningService.replaceScreening(id, screeningDto), HttpStatus.OK);
    }

    @Operation(
            tags = {"Screenings"},
            summary = "Cancel a Screening",
            description = "Specified Screening will get cancelled, all reservations will be deleted and paid Orders will be refunded"
    )
    @PutMapping("{id}/cancel")
    public ResponseEntity<ScreeningFullResponseDto> cancelScreening(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok(screeningService.cancelScreening(id));
    }

    @Operation(
            tags = {"Screenings"}
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") Long id){
        screeningService.deleteScreening(id);
        return ResponseEntity.noContent().build();
    }
}
