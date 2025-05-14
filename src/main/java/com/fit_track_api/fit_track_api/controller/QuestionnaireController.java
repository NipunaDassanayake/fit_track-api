package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.*;
import com.fit_track_api.fit_track_api.model.Questionnaire;
import com.fit_track_api.fit_track_api.model.UserAnswer;
import com.fit_track_api.fit_track_api.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/questions")
public class QuestionnaireController {

    private QuestionnaireService questionnaireService;

    @PostMapping
    // Bulk create questions for a workout plan and return them as DTOs
    public ResponseEntity<List<QuestionDTO>> createQuestions(@RequestBody CreateQuestionsDTO createQuestionsDTO) {
        Long workoutPlanId = createQuestionsDTO.getWorkoutPlanId();
        List<QuestionDTO> createdQuestions = questionnaireService.createQuestionsForWorkoutPlan(createQuestionsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestions);
    }

    @GetMapping("/{workoutPlanId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsForWorkoutPlan(@PathVariable Long workoutPlanId) {
        List<QuestionDTO> questions = questionnaireService.getQuestionsForWorkoutPlan(workoutPlanId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<UserAnswer> submitAnswer(@RequestBody SubmitAnswerDTO submitAnswerDTO) {
        UserAnswer savedAnswer = questionnaireService.submitAnswer(submitAnswerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnswer);
    }

    @PostMapping("/submit-answers")
    public ResponseEntity<List<UserAnswer>> submitBulkAnswers(@RequestBody SubmitBulkAnswersDTO submitBulkAnswersDTO) {
        List<UserAnswer> savedAnswers = questionnaireService.submitBulkAnswers(submitBulkAnswersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnswers);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        questionnaireService.deleteQuestionById(id);
        return ResponseEntity.ok("Question deleted successfully");
    }
    @PutMapping
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody UpdateQuestionDTO dto) {
        System.out.println(dto.getQuestionText());
        System.out.println(dto.getId());
        QuestionDTO updated = questionnaireService.updateQuestion(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionnaireService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }


}
