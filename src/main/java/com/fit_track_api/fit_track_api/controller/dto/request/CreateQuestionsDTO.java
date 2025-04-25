package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class CreateQuestionsDTO {
    private Long workoutPlanId;
    private List<String> questions;
}
