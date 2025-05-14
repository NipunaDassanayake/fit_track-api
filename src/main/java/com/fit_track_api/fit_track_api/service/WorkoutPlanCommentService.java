package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetCommentResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetWorkoutPlanCommentResponseDTO;
import com.fit_track_api.fit_track_api.model.Comment;
import com.fit_track_api.fit_track_api.model.WorkoutPlanComment;

import java.util.List;

public interface WorkoutPlanCommentService {
    WorkoutPlanComment addComment(Long workoutPlanId, Long userId, CreateCommentRequestDTO dto);
    WorkoutPlanComment updateComment(Long workoutPlanCommentId, String newContent);
    void deleteComment(Long workoutPlanCommentId);
    public List<GetWorkoutPlanCommentResponseDTO> getCommentsByWorkoutPlan(Long workoutPlanId);
}
