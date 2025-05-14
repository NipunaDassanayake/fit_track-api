package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateAchievementDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdateAchievementDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.AchievementResponseDTO;
import com.fit_track_api.fit_track_api.model.Achievement;

import java.util.List;

public interface AchievementService {
    public Achievement shareAchievement(Long userId, Long workoutPlanId, CreateAchievementDTO createAchievementDTO);
    public Achievement updateAchievement(Long achievementId, UpdateAchievementDTO updateAchievementDTO);
    public void deleteAchievement(Long userId, Long achievementId);
    public AchievementResponseDTO getAchievementById(Long achievementId);
    public void likeAchievement(Long achievementId, Long userId);
    public void unlikeAchievement(Long achievementId, Long userId);
    public List<AchievementResponseDTO> getAllAchievements();
}

