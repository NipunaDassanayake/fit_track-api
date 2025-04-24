package com.fit_track_api.fit_track_api.controller.dto.response;

import lombok.Data;

@Data
public class ExerciseResponseDTO {
    private Long id;
    private String name;
    private String description;
    private boolean isCompleted;
    private Integer order;
}