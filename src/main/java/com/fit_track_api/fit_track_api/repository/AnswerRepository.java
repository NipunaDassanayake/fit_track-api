//package com.fit_track_api.fit_track_api.repository;
//import com.fit_track_api.fit_track_api.model.Answer;
//import com.fit_track_api.fit_track_api.model.Question;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface AnswerRepository extends JpaRepository<Answer, Long> {
//    List<Answer> findByQuestion(Question question);
//    List<Answer> findByQuestionOrderByCreatedAtAsc(Question question);
//}