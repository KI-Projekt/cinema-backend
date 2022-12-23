package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.pojo.ScreeningPostRequest;
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
    public Screening add(@RequestBody ScreeningPostRequest screeningPostRequest){
        return screeningService.addScreening(
                screeningPostRequest.getDate(),
                screeningPostRequest.getTimeSlot(),
                screeningPostRequest.getMovieId(),
                screeningPostRequest.getRoomId()
        );
    }

    @GetMapping
    public List<Screening> getAll(){
        return screeningService.getAllScreenings();
    }

    @GetMapping("/{id}")
    public Screening getOne(@PathVariable Long id){
        return screeningService.getScreening(id);
    }
}
