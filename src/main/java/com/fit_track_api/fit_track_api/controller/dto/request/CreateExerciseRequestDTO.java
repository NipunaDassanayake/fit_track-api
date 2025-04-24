package com.fit_track_api.fit_track_api.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateExerciseRequestDTO {
    @NotBlank(message = "Exercise name is required")
    @Size(max = 100, message = "Exercise name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Exercise description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Order is required")
    private Integer order;
}
