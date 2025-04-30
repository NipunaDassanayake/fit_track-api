package com.fit_track_api.fit_track_api.service.impl;
import com.cloudinary.Cloudinary;
import com.fit_track_api.fit_track_api.controller.dto.request.CreateAchievementDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdateAchievementDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.AchievementResponseDTO;
import com.fit_track_api.fit_track_api.exceptions.ResourceNotFoundException;
import com.fit_track_api.fit_track_api.exceptions.UnauthorizedException;
import com.fit_track_api.fit_track_api.model.Achievement;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.UserWorkoutPlan;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import com.fit_track_api.fit_track_api.repository.AchievementRepository;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.repository.UserWorkoutPlanRepository;
import com.fit_track_api.fit_track_api.repository.WorkoutPlanRepository;
import com.fit_track_api.fit_track_api.service.AchievementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final UserWorkoutPlanRepository userWorkoutPlanRepository;
    private final Cloudinary cloudinary;

    public Achievement shareAchievement(
            Long userId, Long workoutPlanId, CreateAchievementDTO createAchievementDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        WorkoutPlan plan = workoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan not found"));

        UserWorkoutPlan userWorkoutPlan = userWorkoutPlanRepository.findByUserIdAndWorkoutPlanId(userId, workoutPlanId);
        if (!userWorkoutPlan.isCompleted()) {
            throw new IllegalStateException("Workout plan must be completed to share as an achievement");
        }

        List<String> imageUrls = new ArrayList<>();
        if (createAchievementDTO.getImageUrls() != null && !createAchievementDTO.getImageUrls().isEmpty()) {
            for (MultipartFile image : createAchievementDTO.getImageUrls()) {
                try {
                    String imageUrl = cloudinary.uploader()
                            .upload(image.getBytes(), Map.of(
                                    "public_id", UUID.randomUUID().toString(),
                                    "folder", "achievements"
                            ))
                            .get("url")
                            .toString();
                    imageUrls.add(imageUrl);
                } catch (Exception e) {
                    throw new RuntimeException("Image upload failed", e);
                }
            }
        }

        Achievement achievement = new Achievement();
        achievement.setTitle("Completed Workout Plan: " + plan.getName());
        achievement.setDescription(createAchievementDTO.getDescription());
        achievement.setUser(user);
        achievement.setImageUrl(imageUrls);
        achievement.setWorkoutPlan(plan);

        return achievementRepository.save(achievement);
    }

    @Override
    public Achievement updateAchievement(Long achievementId, UpdateAchievementDTO updateAchievementDTO) {
        Achievement existingAchievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found"));

        existingAchievement.setDescription(updateAchievementDTO.getDescription());

        try {
            if (updateAchievementDTO.getImageUrls() != null && !updateAchievementDTO.getImageUrls().isEmpty()) {
                List<String> uploadedImageUrls = new ArrayList<>();

                for (MultipartFile image : updateAchievementDTO.getImageUrls()) {
                    Map uploadResult = cloudinary.uploader().upload(image.getBytes(), Map.of(
                            "public_id", UUID.randomUUID().toString(),
                            "folder", "achievements"));
                    String imageUrl = (String) uploadResult.get("secure_url");
                    uploadedImageUrls.add(imageUrl);
                }
                existingAchievement.setImageUrl(uploadedImageUrls); // ensure method name matches
            }

            return achievementRepository.save(existingAchievement);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update post with ID: " + achievementId, e);
        }
    }


    @Override
    public void deleteAchievement(Long userId, Long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found"));

        // Verify the achievement belongs to the requesting user
        if (!achievement.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only delete your own achievements");
        }

        achievementRepository.delete(achievement);
    }

    @Override
    public AchievementResponseDTO getAchievementById(Long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement not found with id: " + achievementId));

        AchievementResponseDTO responseDTO = new AchievementResponseDTO();

        // Set basic achievement fields
        responseDTO.setId(achievement.getId());
        responseDTO.setTitle(achievement.getTitle());
        responseDTO.setDescription(achievement.getDescription());
        responseDTO.setAchievedDate(achievement.getAchievedDate());

        // Set user information
        if (achievement.getUser() != null) {
            responseDTO.setUserId(achievement.getUser().getId());
            responseDTO.setUsername(achievement.getUser().getUsername());
        }

        // Set workout plan information
        if (achievement.getWorkoutPlan() != null) {
            responseDTO.setWorkoutPlanId(achievement.getWorkoutPlan().getId());
            responseDTO.setWorkoutPlanName(achievement.getWorkoutPlan().getName());
        }

        return responseDTO;
    }
c


}
