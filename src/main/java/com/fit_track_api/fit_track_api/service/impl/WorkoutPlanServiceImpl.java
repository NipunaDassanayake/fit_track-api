package com.fit_track_api.fit_track_api.service.impl;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateWorkoutPlanRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.ExerciseResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.WorkoutPlanResponseDTO;
import com.fit_track_api.fit_track_api.exceptions.ResourceNotFoundException;
import com.fit_track_api.fit_track_api.model.*;
import com.fit_track_api.fit_track_api.repository.*;
import com.fit_track_api.fit_track_api.service.WorkoutPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final UserWorkoutPlanRepository userWorkoutPlanRepository;
    private final UserExerciseRepository userExerciseRepository;

    @Override
    public WorkoutPlanResponseDTO createPlan(CreateWorkoutPlanRequestDTO createWorkoutPlanRequestDTO, Long userId) {

        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create and save the plan
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName(createWorkoutPlanRequestDTO.getName());
        plan.setDescription(createWorkoutPlanRequestDTO.getDescription());
        plan.setCreator(creator);
        WorkoutPlan savedPlan = workoutPlanRepository.save(plan);

        // Create and save exercises
        List<Exercise> exercises = createWorkoutPlanRequestDTO.getExercises().stream()
                .map(exDto -> {
                    Exercise exercise = new Exercise();
                    exercise.setName(exDto.getName());
                    exercise.setDescription(exDto.getDescription());
                    exercise.setOrder(exDto.getOrder());
                    exercise.setPlan(savedPlan);
                    return exercise;
                })
                .collect(Collectors.toList());

        exerciseRepository.saveAll(exercises);
        savedPlan.setExercises(exercises);

        // Map to response DTO
        List<ExerciseResponseDTO> exerciseResponseDTOs = exercises.stream().map(ex -> {
            ExerciseResponseDTO dto = new ExerciseResponseDTO();
            dto.setId(ex.getId());
            dto.setName(ex.getName());
            dto.setDescription(ex.getDescription());
            dto.setOrder(ex.getOrder());
            return dto;
        }).collect(Collectors.toList());

        WorkoutPlanResponseDTO responseDTO = new WorkoutPlanResponseDTO();
        responseDTO.setId(savedPlan.getId());
        responseDTO.setName(savedPlan.getName());
        responseDTO.setDescription(savedPlan.getDescription());
        responseDTO.setCreatedAt(savedPlan.getCreatedAt());
        responseDTO.setExercises(exerciseResponseDTOs);
        responseDTO.setCreatorId(userId);

        return responseDTO;
    }

    @Override
    public List<WorkoutPlanResponseDTO> getAllPlans() {
        List<WorkoutPlan> plans = workoutPlanRepository.findAll();

        return plans.stream().map(plan -> {
            List<ExerciseResponseDTO> exerciseDTOs = plan.getExercises().stream().map(ex -> {
                ExerciseResponseDTO exDTO = new ExerciseResponseDTO();
                exDTO.setId(ex.getId());
                exDTO.setName(ex.getName());
                exDTO.setDescription(ex.getDescription());
                exDTO.setOrder(ex.getOrder());
                return exDTO;
            }).collect(Collectors.toList());

            WorkoutPlanResponseDTO dto = new WorkoutPlanResponseDTO();
            dto.setId(plan.getId());
            dto.setName(plan.getName());
            dto.setDescription(plan.getDescription());
            dto.setCreatedAt(plan.getCreatedAt());
            dto.setCreatorId(plan.getCreator().getId());
            dto.setExercises(exerciseDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<WorkoutPlanResponseDTO> completeExercise(Long userId, Long workoutPlanId, Long exerciseId) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Fetch the workout plan
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan not found"));

        // Fetch the exercise
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

        // Fetch the user's workout plan participation
        UserWorkoutPlan userWorkoutPlan = userWorkoutPlanRepository.findByUserIdAndWorkoutPlanId(userId, workoutPlanId);
        if (userWorkoutPlan == null) {
            throw new ResourceNotFoundException("User is not participating in this workout plan");
        }

        // Check if the exercise is part of the workout plan for the user
        boolean isExerciseInPlan = workoutPlan.getExercises().stream()
                .anyMatch(ex -> ex.getId().equals(exerciseId));

        if (!isExerciseInPlan) {
            throw new IllegalStateException("This exercise is not part of the workout plan for the user");
        }

        // Find the user-exercise relationship for this workout plan
        UserExercise userExercise = userExerciseRepository.findByUserWorkoutPlanIdAndExerciseId(userWorkoutPlan.getId(), exerciseId);

        // If the exercise is not found, throw an error because it's not part of the user’s workout plan
        if (userExercise == null) {
            throw new ResourceNotFoundException("Exercise is not found for this user in the workout plan");
        }

        // Mark the exercise as completed
        userExercise.setCompleted(true);
        userExerciseRepository.save(userExercise);

        // Update the UserWorkoutPlan's completion status
        userWorkoutPlan.updateCompletionStatus();
        userWorkoutPlanRepository.save(userWorkoutPlan);

        // Map to response DTO
        List<ExerciseResponseDTO> exerciseResponseDTOs = workoutPlan.getExercises().stream().map(ex -> {
            ExerciseResponseDTO dto = new ExerciseResponseDTO();
            dto.setId(ex.getId());
            dto.setName(ex.getName());
            dto.setDescription(ex.getDescription());
            dto.setOrder(ex.getOrder());
            return dto;
        }).collect(Collectors.toList());

        WorkoutPlanResponseDTO responseDTO = new WorkoutPlanResponseDTO();
        responseDTO.setId(workoutPlan.getId());
        responseDTO.setName(workoutPlan.getName());
        responseDTO.setDescription(workoutPlan.getDescription());
        responseDTO.setCreatedAt(workoutPlan.getCreatedAt());
        responseDTO.setExercises(exerciseResponseDTOs);
        responseDTO.setCreatorId(workoutPlan.getCreator().getId());

        return ResponseEntity.ok(responseDTO);
    }


    @Override
    public WorkoutPlan participateInWorkoutPlan(Long userId, Long workoutPlanId) {
        // Fetch the user and workout plan
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan not found"));

        // Check if the user is already participating in the workout plan
        UserWorkoutPlan existingParticipation = userWorkoutPlanRepository.findByUserIdAndWorkoutPlanId(userId, workoutPlanId);
        if (existingParticipation != null) {
            throw new IllegalStateException("User is already participating in this workout plan");
        }

        // Add the user to the list of participators
        UserWorkoutPlan userWorkoutPlan = new UserWorkoutPlan();
        userWorkoutPlan.setUser(user);
        userWorkoutPlan.setWorkoutPlan(workoutPlan);
        userWorkoutPlan.setCompleted(false);     // Initially not completed

        // Save the updated workout plan
        userWorkoutPlanRepository.save(userWorkoutPlan);

        // Create UserExercise records for each exercise
        workoutPlan.getExercises().forEach(exercise -> {
            UserExercise userExercise = new UserExercise();
            userExercise.setUserWorkoutPlan(userWorkoutPlan);
            userExercise.setExercise(exercise);
            userExercise.setUser(user);
            userExercise.setCompleted(false);  // Initially not completed
            userExerciseRepository.save(userExercise);
        });

        return workoutPlan;
    }

    @Override
    public void deleteWorkoutPlan(Long planId) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan not found"));

        // Delete related user participations and user exercises
        List<UserWorkoutPlan> userWorkoutPlans = userWorkoutPlanRepository.findByWorkoutPlanId(planId);
        for (UserWorkoutPlan userWorkoutPlan : userWorkoutPlans) {
            userExerciseRepository.deleteAll(userExerciseRepository.findByUserWorkoutPlanId(userWorkoutPlan.getId()));
        }
        userWorkoutPlanRepository.deleteAll(userWorkoutPlans);

        // Delete the workout plan (thanks to cascade = ALL, exercises and questionnaires get deleted too)
        workoutPlanRepository.delete(workoutPlan);
    }

}
