package com.fit_track_api.fit_track_api.controller;


import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetWorkoutPlanCommentResponseDTO;
import com.fit_track_api.fit_track_api.model.WorkoutPlanComment;
import com.fit_track_api.fit_track_api.service.WorkoutPlanCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WorkoutPlanCommentController {


    private WorkoutPlanCommentService commentService;


    @PostMapping("/workoutPlan/{workoutPlanId}/user/{userId}/comment")
    public WorkoutPlanComment addComment(@PathVariable Long workoutPlanId , @PathVariable Long userId , @RequestBody CreateCommentRequestDTO createCommentRequestDTO){
        System.out.println(workoutPlanId);
        System.out.println(userId);
        return commentService.addComment(workoutPlanId,userId,createCommentRequestDTO);

    }

    @PutMapping("/{commentId}")
    public ResponseEntity<WorkoutPlanComment> updateComment(@PathVariable Long workoutPlanId, @RequestParam String newContent){
        WorkoutPlanComment updatedComment = commentService.updateComment(workoutPlanId, newContent);
        return ResponseEntity.ok(updatedComment);

    }
    @DeleteMapping("/workoutPlanComment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/workoutPlan/{workoutPlanId}")
    public ResponseEntity<List<GetWorkoutPlanCommentResponseDTO>> getCommentsByWorkoutPlan(@PathVariable Long workoutPlanId){
        List<GetWorkoutPlanCommentResponseDTO> comments = commentService.getCommentsByWorkoutPlan(workoutPlanId);
        return ResponseEntity.ok(comments);
    }


}

