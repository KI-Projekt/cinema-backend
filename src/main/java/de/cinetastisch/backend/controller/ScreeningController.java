package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.info.ScreeningInfo;
import de.cinetastisch.backend.service.ScreeningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public List<Screening> getAll(){
        return screeningService.getAllScreenings();
    }

    @GetMapping("/{id}")
    public Screening getOne(@PathVariable Long id){
        return screeningService.getScreening(id);
    }

    @PostMapping
    public ResponseEntity<Screening> add(@Valid @RequestBody ScreeningInfo screeningInfo){
        return new ResponseEntity<>(screeningService.addScreening(screeningInfo), HttpStatus.CREATED);
    }
}
