package com.fit_track_api.fit_track_api.repository;

import com.fit_track_api.fit_track_api.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}
