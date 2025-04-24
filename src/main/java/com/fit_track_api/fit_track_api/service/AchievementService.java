package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.AchievementDTO;
import com.fit_track_api.fit_track_api.model.Achievement;

public interface AchievementService {
    public AchievementDTO shareWorkoutPlanAsAchievement(Long userId, Long workoutPlanId);
}
