package com.fit_track_api.fit_track_api.service.impl;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetCommentResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetWorkoutPlanCommentResponseDTO;
import com.fit_track_api.fit_track_api.model.*;
import com.fit_track_api.fit_track_api.repository.*;
import com.fit_track_api.fit_track_api.service.WorkoutPlanCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutPlanCommentServiceImpl implements WorkoutPlanCommentService {
    private WorkoutPlanCommentRepository workoutPlanCommentRepository;
    private WorkoutPlanRepository workoutPlanRepository;
    private UserRepository userRepository;


    @Override
    public WorkoutPlanComment addComment(Long workoutPlanId, Long userId, CreateCommentRequestDTO dto) {
        WorkoutPlan workoutPlan1 = workoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new RuntimeException("workoutPlan not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutPlanComment comment = new WorkoutPlanComment();
        comment.setContent(dto.getContent());
        comment.setWorkoutPlan(workoutPlan1);
        comment.setUser(user);

        return workoutPlanCommentRepository.save(comment);
    }

    @Override
    public WorkoutPlanComment updateComment(Long workoutPlanCommentId, String newContent) {
        WorkoutPlanComment comment = workoutPlanCommentRepository.findById(workoutPlanCommentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + workoutPlanCommentId));

        comment.setContent(newContent);
        return workoutPlanCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long workoutPlanCommentId) {
        if (!workoutPlanCommentRepository.existsById(workoutPlanCommentId)) {
            throw new RuntimeException("Comment not found with id: " + workoutPlanCommentId);
        }
        workoutPlanCommentRepository.deleteById(workoutPlanCommentId);
    }

    @Override
    public List<GetWorkoutPlanCommentResponseDTO> getCommentsByWorkoutPlan(Long workoutPlanId) {
        List<WorkoutPlanComment> comments = workoutPlanCommentRepository.findByWorkoutPlanId(workoutPlanId);
        return comments.stream()
                .map(comment -> {
                    GetWorkoutPlanCommentResponseDTO dto = new GetWorkoutPlanCommentResponseDTO();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setCreatedAt(comment.getCreatedAt());
                    dto.setUserId(comment.getUser().getId());
                    dto.setUsername(comment.getUser().getUsername());
                    dto.setWorkOutPlanId(comment.getWorkoutPlan().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
