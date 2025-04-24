package com.fit_track_api.fit_track_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_workout_plan_id", nullable = false)
    private UserWorkoutPlan userWorkoutPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private boolean isCompleted = false;  // Tracks whether the user has completed this exercise
}
