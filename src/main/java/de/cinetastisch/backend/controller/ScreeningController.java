package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.dto.ScreeningRequestDto;
import de.cinetastisch.backend.service.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                    @Parameter(
                            name = "startTime",
                            description = "Get all screenings after the specified start datetime",
                            example = "2022-12-30T19:34:50.63"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Screening>> getAll(@RequestParam(value = "startTime", required = false) String ldtStart){
        return ResponseEntity.ok(screeningService.getAllScreenings(ldtStart));
    }

    @Operation(
            tags = {"Screenings"}
    )
    @GetMapping("/{id}")
    public Screening getOne(@PathVariable Long id){
        return screeningService.getScreening(id);
    }

    @Operation(
            tags = {"Screenings"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ScreeningRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @PostMapping
    public ResponseEntity<Screening> add(@Valid @RequestBody ScreeningRequestDto screeningRequestDto){
        return new ResponseEntity<>(screeningService.addScreening(screeningRequestDto), HttpStatus.CREATED);
    }


    @Operation(
            tags = {"Screenings"}
    )
    @PutMapping("{id}")
    public ResponseEntity<Screening> replaceOne(@Valid @RequestBody ScreeningRequestDto screeningDto,
                                                @Valid @PathVariable("id") Long id){
        return new ResponseEntity<>(screeningService.replaceScreening(id, screeningDto), HttpStatus.OK);
    }

}
