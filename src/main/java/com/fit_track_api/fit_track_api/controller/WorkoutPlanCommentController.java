package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetWorkoutPlanCommentResponseDTO;
import com.fit_track_api.fit_track_api.model.WorkoutPlanComment;
import com.fit_track_api.fit_track_api.service.WorkoutPlanCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workoutPlans")
@AllArgsConstructor
public class WorkoutPlanCommentController {

    private final WorkoutPlanCommentService commentService;

    @PostMapping("/{workoutPlanId}/comments")
    public ResponseEntity<WorkoutPlanComment> addComment(
            @PathVariable Long workoutPlanId,
            @RequestParam Long userId,
            @RequestBody CreateCommentRequestDTO createCommentRequestDTO) {
        WorkoutPlanComment comment = commentService.addComment(workoutPlanId, userId, createCommentRequestDTO);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDTO updateDto) {
        WorkoutPlanComment updatedComment = commentService.updateComment(commentId, updateDto);
        return ResponseEntity.ok("Successfully updated Comment");
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{workoutPlanId}/comments")
    public ResponseEntity<List<GetWorkoutPlanCommentResponseDTO>> getCommentsByWorkoutPlan(
            @PathVariable Long workoutPlanId) {
        List<GetWorkoutPlanCommentResponseDTO> comments = commentService.getCommentsByWorkoutPlan(workoutPlanId);
        return ResponseEntity.ok(comments);
    }
}
