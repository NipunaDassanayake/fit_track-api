package com.fit_track_api.fit_track_api.repository;

import com.fit_track_api.fit_track_api.model.Exercise;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByPlan(WorkoutPlan plan);
//    List<Exercise> findByPlanAndIsCompleted(WorkoutPlan plan, boolean isCompleted);
}
