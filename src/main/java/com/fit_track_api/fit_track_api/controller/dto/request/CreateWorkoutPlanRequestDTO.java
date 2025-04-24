package com.fit_track_api.fit_track_api.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateWorkoutPlanRequestDTO {
    @NotBlank(message = "Plan name is required")
    @Size(max = 100, message = "Plan name cannot exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Exercises list cannot be null")
    @Size(min = 1, message = "At least one exercise is required")
    private List<CreateExerciseRequestDTO> exercises;


}
