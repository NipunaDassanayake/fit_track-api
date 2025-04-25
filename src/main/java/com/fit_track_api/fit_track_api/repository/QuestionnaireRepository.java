package com.fit_track_api.fit_track_api.repository;

import com.fit_track_api.fit_track_api.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    List<Questionnaire> findByWorkoutPlanId(Long workoutPlanId);
}

