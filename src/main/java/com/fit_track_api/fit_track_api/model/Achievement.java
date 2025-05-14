package com.fit_track_api.fit_track_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    private List<String> imageUrl;

    private LocalDate achievedDate = LocalDate.now();

//    private String templateType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id")
    @JsonManagedReference
    private WorkoutPlan workoutPlan;

    @Column(nullable = false)
    private int likedCount = 0;

    @OneToMany(mappedBy = "achievement")
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToMany
    @JoinTable(
            name = "achievement_likes",
            joinColumns = @JoinColumn(name = "achievement_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedBy = new ArrayList<>();
}