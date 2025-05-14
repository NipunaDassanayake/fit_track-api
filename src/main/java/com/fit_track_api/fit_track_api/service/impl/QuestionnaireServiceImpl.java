package com.fit_track_api.fit_track_api.service.impl;

import com.fit_track_api.fit_track_api.controller.dto.request.*;
import com.fit_track_api.fit_track_api.exceptions.ResourceNotFoundException;
import com.fit_track_api.fit_track_api.model.Questionnaire;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.UserAnswer;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import com.fit_track_api.fit_track_api.repository.QuestionnaireRepository;
import com.fit_track_api.fit_track_api.repository.UserAnswerRepository;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.repository.WorkoutPlanRepository;
import com.fit_track_api.fit_track_api.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private UserRepository userRepository;
    private WorkoutPlanRepository workoutPlanRepository;
    private QuestionnaireRepository questionnaireRepository;
    private UserAnswerRepository userAnswerRepository;

    @Override
    public List<QuestionDTO> createQuestionsForWorkoutPlan(CreateQuestionsDTO createQuestionsDTO) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(createQuestionsDTO.getWorkoutPlanId())
                .orElseThrow(() -> new RuntimeException("Workout plan not found"));

        List<QuestionDTO> createdQuestionsDTO = new ArrayList<>();

        for (String questionText : createQuestionsDTO.getQuestions()) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setQuestionText(questionText);
            questionnaire.setWorkoutPlan(workoutPlan);
            questionnaireRepository.save(questionnaire);

            createdQuestionsDTO.add(new QuestionDTO(questionnaire.getId(), questionnaire.getQuestionText()));
        }

        return createdQuestionsDTO;
    }

    @Override
    public List<QuestionDTO> getQuestionsForWorkoutPlan(Long workoutPlanId) {
        List<Questionnaire> questionnaires = questionnaireRepository.findByWorkoutPlanId(workoutPlanId);

        if (questionnaires.isEmpty()) {
            throw new ResourceNotFoundException("No questions found for workout plan with ID: " + workoutPlanId);
        }

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaires) {
            questionDTOs.add(new QuestionDTO(questionnaire.getId(), questionnaire.getQuestionText()));
        }

        return questionDTOs;
    }

    @Override
    public UserAnswer submitAnswer(SubmitAnswerDTO submitAnswerDTO) {
        // Find the user
        User user = userRepository.findById(submitAnswerDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the question (questionnaire)
        Questionnaire questionnaire = questionnaireRepository.findById(submitAnswerDTO.getQuestionnaireId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Create the user answer
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUser(user);
        userAnswer.setQuestionnaire(questionnaire);
        userAnswer.setAnswer(submitAnswerDTO.getAnswer());

        // Save the answer to the database
        return userAnswerRepository.save(userAnswer);
    }

    @Override
    public List<UserAnswer> submitBulkAnswers(SubmitBulkAnswersDTO submitBulkAnswersDTO) {
        // Find the user
        User user = userRepository.findById(submitBulkAnswersDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // List to hold the saved user answers
        List<UserAnswer> savedAnswers = new ArrayList<>();

        // Loop through each answer in the bulk submission
        for (SubmitBulkAnswersDTO.AnswerDTO answerDTO : submitBulkAnswersDTO.getAnswers()) {
            // Find the question (questionnaire)
            Questionnaire questionnaire = questionnaireRepository.findById(answerDTO.getQuestionnaireId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            // Create a new UserAnswer object and set values
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUser(user);
            userAnswer.setQuestionnaire(questionnaire);
            userAnswer.setAnswer(answerDTO.getAnswer());

            // Save the answer to the database
            savedAnswers.add(userAnswerRepository.save(userAnswer));
        }

        return savedAnswers;
    }

    @Override
    public void deleteQuestionById(Long questionId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        // Optional: delete associated user answers first (if cascading isn't configured)
        List<UserAnswer> userAnswers = userAnswerRepository.findByQuestionnaireId(questionId);
        userAnswerRepository.deleteAll(userAnswers);

        // Now delete the question itself
        questionnaireRepository.delete(questionnaire);
    }

    @Override
    public QuestionDTO updateQuestion(UpdateQuestionDTO updateQuestionDTO) {
        Questionnaire question = questionnaireRepository.findById(updateQuestionDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        question.setQuestionText(updateQuestionDTO.getQuestionText());
        Questionnaire updated = questionnaireRepository.save(question);

        QuestionDTO dto = new QuestionDTO();
        dto.setId(updated.getId());
        dto.setQuestionText(updated.getQuestionText());
        return dto;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Questionnaire> questionnaires = questionnaireRepository.findAll();

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaires) {
            questionDTOs.add(new QuestionDTO(questionnaire.getId(), questionnaire.getQuestionText()));
        }

        return questionDTOs;
    }


}
