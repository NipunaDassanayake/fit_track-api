package com.fit_track_api.fit_track_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    // User's workout posts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WorkoutPost> workoutPosts = new ArrayList<>();

    // Workout plans created by the user
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WorkoutPlan> workoutPlans = new ArrayList<>();

    // Workout plans saved by the user
    @ManyToMany
    @JoinTable(
            name = "user_saved_plans",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id")
    )
    @JsonManagedReference
    private List<WorkoutPlan> savedPlans = new ArrayList<>();

    // Users followed by this user
    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    @JsonManagedReference
    private List<User> following = new ArrayList<>();

    // Users following the current user
    @ManyToMany(mappedBy = "following")
    @JsonBackReference
    private List<User> followers = new ArrayList<>();

    // Optional: Uncomment if you want to manage the saved plans explicitly
    /*
    public void addSavedPlan(WorkoutPlan plan) {
        this.savedPlans.add(plan);
        plan.getSavedBy().add(this);
    }

    public void removeSavedPlan(WorkoutPlan plan) {
        this.savedPlans.remove(plan);
        plan.getSavedBy().remove(this);
    }
    */

    @OneToMany(mappedBy = "user")
    private List<UserAnswer> userAnswers;



}
