// AchievementController.java
package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateAchievementDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdateAchievementDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.AchievementResponseDTO;
import com.fit_track_api.fit_track_api.model.Achievement;
import com.fit_track_api.fit_track_api.service.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping(value = "/share-achievement/{workoutPlanId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> shareAsAchievement(
            @RequestParam Long userId,
            @PathVariable Long workoutPlanId,
            @ModelAttribute CreateAchievementDTO createAchievementDTO) {

        Achievement achievement = achievementService.shareAchievement(userId, workoutPlanId, createAchievementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Achievement shared successfully");

    }


    @PutMapping("/{achievementId}")
    public ResponseEntity<String> updateAchievement(
            @PathVariable Long achievementId,
            @RequestBody UpdateAchievementDTO updateAchievementDTO) {
        System.out.println(updateAchievementDTO.getDescription());
        Achievement updatedAchievement = achievementService.updateAchievement(achievementId, updateAchievementDTO);
        return ResponseEntity.ok("Achievement updated successfully ");
    }

    @DeleteMapping("/users/{userId}/achievements/{achievementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAchievement(
            @PathVariable Long userId,
            @PathVariable Long achievementId) {
        achievementService.deleteAchievement(userId, achievementId);
    }

    @GetMapping("/{achievementId}")
    public ResponseEntity<AchievementResponseDTO> getAchievementById(
            @PathVariable Long achievementId) {
        AchievementResponseDTO achievement = achievementService.getAchievementById(achievementId);
        return ResponseEntity.ok(achievement);
    }

    @PostMapping("/{achievementId}/like/{userId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long achievementId,
            @PathVariable Long userId) {
        achievementService.likeAchievement(achievementId, userId);
        return ResponseEntity.ok("achievement liked successfully");
    }

    @PostMapping("/{achievementId}/unlike/{userId}")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long achievementId,
            @PathVariable Long userId) {
        achievementService.unlikeAchievement(achievementId, userId);
        return ResponseEntity.ok("achievement unliked successfully");
    }
}
