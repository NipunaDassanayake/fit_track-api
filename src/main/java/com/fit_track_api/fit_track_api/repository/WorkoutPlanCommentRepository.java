package com.fit_track_api.fit_track_api.repository;

import com.fit_track_api.fit_track_api.model.Comment;
import com.fit_track_api.fit_track_api.model.WorkoutPlanComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanCommentRepository extends JpaRepository<WorkoutPlanComment, Long> {
    List<WorkoutPlanComment> findByWorkoutPlanId(Long workoutPlanId);
}
