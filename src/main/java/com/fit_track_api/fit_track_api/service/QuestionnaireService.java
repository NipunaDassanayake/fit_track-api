package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.*;
import com.fit_track_api.fit_track_api.model.Questionnaire;
import com.fit_track_api.fit_track_api.model.UserAnswer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionnaireService {
    public List<QuestionDTO> createQuestionsForWorkoutPlan(CreateQuestionsDTO createQuestionsDTO) ;
    public List<QuestionDTO> getQuestionsForWorkoutPlan(Long workoutPlanId);
    public UserAnswer submitAnswer(SubmitAnswerDTO submitAnswerDTO);
    public List<UserAnswer> submitBulkAnswers(SubmitBulkAnswersDTO submitBulkAnswersDTO);
    void deleteQuestionById(Long questionId);
    QuestionDTO updateQuestion(UpdateQuestionDTO updateQuestionDTO);
    List<QuestionDTO> getAllQuestions();


}