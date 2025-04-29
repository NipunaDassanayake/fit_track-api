package com.fit_track_api.fit_track_api.repository;

import com.fit_track_api.fit_track_api.model.UserWorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWorkoutPlanRepository extends JpaRepository<UserWorkoutPlan, Long> {
    UserWorkoutPlan findByUserIdAndWorkoutPlanId(Long userId, Long workoutPlanId);

    List<UserWorkoutPlan> findByWorkoutPlanId(Long planId);
}
