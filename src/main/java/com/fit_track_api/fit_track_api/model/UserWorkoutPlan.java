package com.fit_track_api.fit_track_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class UserWorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    private WorkoutPlan workoutPlan;

    @Column(nullable = false)
    private boolean isCompleted = false;  // Tracks whether this user has completed the workout plan

    @OneToMany(mappedBy = "userWorkoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExercise> userExercises;  // Tracks the exercises completed by the user

    // Automatically updates the isCompleted flag based on the completion of all exercises
    public void updateCompletionStatus() {
        this.isCompleted = userExercises.stream().allMatch(UserExercise::isCompleted);
    }
}
