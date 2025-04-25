//package com.fit_track_api.fit_track_api.model;
//import jakarta.persistence.*;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//public class Answer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "question_id", nullable = false)
//    private Question question;
//}
