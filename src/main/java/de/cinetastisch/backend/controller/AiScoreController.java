package de.cinetastisch.backend.controller;


import de.cinetastisch.backend.service.AiScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai-scores")
public class AiScoreController {

    @Autowired
    private AiScoreService aiScoreService;


    @GetMapping("/{userId}")
    public ResponseEntity<?> getScores(@PathVariable Long userId) {
        return ResponseEntity.ok(aiScoreService.getAiScore(userId));
    }

}
