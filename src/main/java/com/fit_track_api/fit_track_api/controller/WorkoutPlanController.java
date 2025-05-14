package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateWorkoutPlanRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.WorkoutPlanResponseDTO;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import com.fit_track_api.fit_track_api.service.WorkoutPlanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workoutPlan")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;


    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WorkoutPlanResponseDTO> createWorkoutPlan(
            @PathVariable Long userId,
            @Valid @ModelAttribute CreateWorkoutPlanRequestDTO requestDTO) {

        WorkoutPlanResponseDTO responseDTO = workoutPlanService.createPlan(requestDTO, userId);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getAllWorkoutPlans() {
        List<WorkoutPlanResponseDTO> plans = workoutPlanService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @PatchMapping("/complete-exercise/{userId}/{workoutPlanId}/{exerciseId}")
    public ResponseEntity<WorkoutPlanResponseDTO> completeExercise(
            @PathVariable Long userId,
            @PathVariable Long workoutPlanId,
            @PathVariable Long exerciseId) {
        return workoutPlanService.completeExercise(userId, workoutPlanId, exerciseId);
    }

    // API endpoint to participate in a workout plan
    @PostMapping("/participate/{workoutPlanId}")
    public ResponseEntity<String> participateInWorkoutPlan(@RequestParam Long userId, @PathVariable Long workoutPlanId) {
        WorkoutPlan workoutPlan = workoutPlanService.participateInWorkoutPlan(userId, workoutPlanId);
        return ResponseEntity.ok("User Successfully Participated in Workout Plan");
    }


    @DeleteMapping("/{planId}")
    public ResponseEntity<String> deleteWorkoutPlan(@PathVariable Long planId) {
        workoutPlanService.deleteWorkoutPlan(planId);
        return ResponseEntity.ok("Workout plan deleted successfully");
    }

    @PostMapping("/{workoutPlanId}/like/{userId}")
    public ResponseEntity<String> likeWorkoutPlan(@PathVariable Long workoutPlanId, @PathVariable Long userId) {
        workoutPlanService.likeWorkoutPlan(workoutPlanId, userId);
        return ResponseEntity.ok("Workout plan liked successfully.");
    }

    @PostMapping("/{workoutPlanId}/unlike/{userId}")
    public ResponseEntity<String> unlikeWorkoutPlan(@PathVariable Long workoutPlanId, @PathVariable Long userId) {
        workoutPlanService.unlikeWorkoutPlan(workoutPlanId, userId);
        return ResponseEntity.ok("Workout plan unliked successfully.");
    }

}
