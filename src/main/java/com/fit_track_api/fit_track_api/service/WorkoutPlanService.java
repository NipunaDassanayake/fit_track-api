package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateWorkoutPlanRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.WorkoutPlanResponseDTO;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WorkoutPlanService {
    public WorkoutPlanResponseDTO createPlan(CreateWorkoutPlanRequestDTO createWorkoutPlanRequestDTO, Long userId);
    List<WorkoutPlanResponseDTO> getAllPlans();
    public ResponseEntity<WorkoutPlanResponseDTO> completeExercise(Long userId, Long workoutPlanId, Long exerciseId);
    public WorkoutPlan participateInWorkoutPlan(Long userId, Long workoutPlanId);
    void deleteWorkoutPlan(Long planId);




    }