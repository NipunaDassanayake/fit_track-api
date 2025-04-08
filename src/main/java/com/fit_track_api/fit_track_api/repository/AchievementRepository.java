package com.fit_track_api.fit_track_api.repository;
import com.fit_track_api.fit_track_api.model.Achievement;
import com.fit_track_api.fit_track_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findByUser(User user);
    List<Achievement> findByUserOrderByAchievedDateDesc(User user);
}