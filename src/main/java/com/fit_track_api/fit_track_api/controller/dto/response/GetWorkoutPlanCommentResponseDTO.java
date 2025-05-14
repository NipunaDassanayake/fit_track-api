package com.fit_track_api.fit_track_api.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class GetWorkoutPlanCommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    private Long workOutPlanId;
}
