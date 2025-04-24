package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.AchievementDTO;
import com.fit_track_api.fit_track_api.model.Achievement;
import com.fit_track_api.fit_track_api.service.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping("/share-achievement/{workoutPlanId}")
    public ResponseEntity<AchievementDTO> shareAsAchievement(@PathVariable Long workoutPlanId, @RequestParam Long userId) {
        AchievementDTO achievement = achievementService.shareWorkoutPlanAsAchievement(userId, workoutPlanId);
        return ResponseEntity.ok(achievement);
    }
}
