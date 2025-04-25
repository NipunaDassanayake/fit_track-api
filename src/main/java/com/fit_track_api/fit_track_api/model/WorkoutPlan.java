package com.fit_track_api.fit_track_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Creator of the plan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    // List of participators (users who have joined the plan)
    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<UserWorkoutPlan> participators = new ArrayList<>();

    // Exercises directly in the plan (no sections)
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC") // Exercises ordered by their 'order' field
    private List<Exercise> exercises = new ArrayList<>();

    // Relationship with Achievement (Workout Plan can have multiple Achievements)
    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Achievement> achievements = new ArrayList<>();

    @OneToMany(mappedBy = "workoutPlan")
    private List<Questionnaire> questionnaires;


}
