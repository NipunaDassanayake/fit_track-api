package com.fit_track_api.fit_track_api.repository;
import com.fit_track_api.fit_track_api.model.WorkoutPost;
import com.fit_track_api.fit_track_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkoutPostRepository extends JpaRepository<WorkoutPost, Long> {
    List<WorkoutPost> findByUser(User user);
    List<WorkoutPost> findByUserInOrderByCreatedAtDesc(List<User> users);
    List<WorkoutPost> findAllByOrderByCreatedAtDesc();

    List<WorkoutPost> findByUserId(Long userId);
}
