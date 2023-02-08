package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.service.OpeningHourService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/openinghours")
public class OpeningHourController {

    OpeningHourService openingHourService;

    public OpeningHourController(OpeningHourService openingHourService) {
        this.openingHourService = openingHourService;
    }

    @Operation(
            tags = {"Opening Hours"}
    )
    @GetMapping
    public ResponseEntity<List<OpeningHour>> getAll(){
        return ResponseEntity.ok(openingHourService.getAllOpeningHours());
    }

    @Operation(
            tags = {"Opening Hours"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<OpeningHour> replace(@PathVariable("id")Long id, @RequestBody OpeningHour openingHour){
        return ResponseEntity.ok(openingHourService.replaceOpeningHour(id, openingHour));
    }

}
