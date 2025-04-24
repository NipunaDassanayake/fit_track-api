package com.fit_track_api.fit_track_api.service.impl;

import com.fit_track_api.fit_track_api.controller.dto.request.AchievementDTO;
import com.fit_track_api.fit_track_api.exceptions.ResourceNotFoundException;
import com.fit_track_api.fit_track_api.model.Achievement;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.UserWorkoutPlan;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import com.fit_track_api.fit_track_api.repository.AchievementRepository;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.repository.UserWorkoutPlanRepository;
import com.fit_track_api.fit_track_api.repository.WorkoutPlanRepository;
import com.fit_track_api.fit_track_api.service.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final UserWorkoutPlanRepository userWorkoutPlanRepository;

    @Override
    public AchievementDTO shareWorkoutPlanAsAchievement(Long userId, Long workoutPlanId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        WorkoutPlan plan = workoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan not found"));

        UserWorkoutPlan userWorkoutPlan = userWorkoutPlanRepository.findByUserIdAndWorkoutPlanId(userId, workoutPlanId);
        if (!userWorkoutPlan.isCompleted()) {
            throw new IllegalStateException("Workout plan must be completed to share as an achievement");
        }

        Achievement achievement = new Achievement();
        achievement.setTitle("Completed Workout Plan: " + plan.getName());
        achievement.setDescription("Congratulations! You've completed the workout plan " + plan.getName() + " on " + LocalDate.now());
        achievement.setUser(user);
        achievement.setWorkoutPlan(plan);

        Achievement savedAchievement = achievementRepository.save(achievement);

        // Map to DTO
        AchievementDTO achievementDTO = new AchievementDTO();
        achievementDTO.setId(savedAchievement.getId());
        achievementDTO.setTitle(savedAchievement.getTitle());
        achievementDTO.setDescription(savedAchievement.getDescription());
        achievementDTO.setAchievedDate(savedAchievement.getAchievedDate());
        achievementDTO.setUserId(savedAchievement.getUser().getId());
        achievementDTO.setWorkoutPlanId(savedAchievement.getWorkoutPlan().getId());

        return achievementDTO;
    }


}
