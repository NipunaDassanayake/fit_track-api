package com.fit_track_api.fit_track_api.repository;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
//    List<WorkoutPlan> findByUser(User user);
//    List<WorkoutPlan> findByIsPublicTrueOrderByIdDesc();
//    List<WorkoutPlan> findByCreatorInAndIsPublicTrueOrderByIdDesc(List<User> users);
//    List<WorkoutPlan> findByCreator(User user);
//    List<WorkoutPlan> findByCreatorAndIsCompleted(User user, boolean isCompleted);
}