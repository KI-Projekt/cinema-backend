package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.dto.ScreeningDto;
import de.cinetastisch.backend.model.info.ScreeningInfo;
import de.cinetastisch.backend.service.ScreeningService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping
    public Screening add(@RequestBody ScreeningInfo screeningInfo){
        return screeningService.addScreening(
                screeningInfo.getDate(),
                screeningInfo.getTimeSlot(),
                screeningInfo.getMovieId(),
                screeningInfo.getRoomId()
        );
    }

    @GetMapping
    public List<ScreeningDto> getAll(){
        return screeningService.getAllScreenings();
    }

    @GetMapping("/{id}")
    public ScreeningDto getOne(@PathVariable Long id){
        return screeningService.getScreening(id);
    }
}
