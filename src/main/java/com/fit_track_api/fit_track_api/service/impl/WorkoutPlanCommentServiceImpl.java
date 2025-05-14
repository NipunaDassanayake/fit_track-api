package com.fit_track_api.fit_track_api.service.impl;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetWorkoutPlanCommentResponseDTO;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import com.fit_track_api.fit_track_api.model.WorkoutPlanComment;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.repository.WorkoutPlanCommentRepository;
import com.fit_track_api.fit_track_api.repository.WorkoutPlanRepository;
import com.fit_track_api.fit_track_api.service.WorkoutPlanCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutPlanCommentServiceImpl implements WorkoutPlanCommentService {

    private final WorkoutPlanCommentRepository workoutPlanCommentRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;

    @Override
    public WorkoutPlanComment addComment(Long workoutPlanId, Long userId, CreateCommentRequestDTO dto) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new RuntimeException("WorkoutPlan not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutPlanComment comment = new WorkoutPlanComment();
        comment.setContent(dto.getContent());
        comment.setWorkoutPlan(workoutPlan);
        comment.setUser(user);

        return workoutPlanCommentRepository.save(comment);
    }

    @Override
    public WorkoutPlanComment updateComment(Long commentId, UpdateCommentRequestDTO dto) {
        WorkoutPlanComment comment = workoutPlanCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        comment.setContent(dto.getContent());
        return workoutPlanCommentRepository.save(comment);
    }


    @Override
    public void deleteComment(Long commentId) {
        if (!workoutPlanCommentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
        workoutPlanCommentRepository.deleteById(commentId);
    }

    @Override
    public List<GetWorkoutPlanCommentResponseDTO> getCommentsByWorkoutPlan(Long workoutPlanId) {
        List<WorkoutPlanComment> comments = workoutPlanCommentRepository.findByWorkoutPlanId(workoutPlanId);
        return comments.stream().map(comment -> {
            GetWorkoutPlanCommentResponseDTO dto = new GetWorkoutPlanCommentResponseDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setCreatedAt(comment.getCreatedAt());
            dto.setUserId(comment.getUser().getId());
            dto.setUsername(comment.getUser().getUsername());
            dto.setWorkOutPlanId(comment.getWorkoutPlan().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
