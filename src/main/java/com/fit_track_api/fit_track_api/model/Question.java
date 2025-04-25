//package com.fit_track_api.fit_track_api.model;
//import jakarta.persistence.*;
//import lombok.Data;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//
//public class Question {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false, length = 2000)
//    private String content;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("createdAt ASC")
//    private List<Answer> answers = new ArrayList<>();
//}