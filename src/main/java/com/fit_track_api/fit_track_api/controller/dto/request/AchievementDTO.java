package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class AchievementDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate achievedDate;
    private Long userId;
    private Long workoutPlanId;

    // Constructor, getters and setters
}
