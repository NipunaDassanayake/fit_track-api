package com.fit_track_api.fit_track_api.controller.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutPlanResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Long creatorId;
    private List<ExerciseResponseDTO> exercises;
}
