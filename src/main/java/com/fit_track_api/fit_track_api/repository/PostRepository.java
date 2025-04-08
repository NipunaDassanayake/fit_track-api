package com.fit_track_api.fit_track_api.repository;
import com.fit_track_api.fit_track_api.model.Post;
import com.fit_track_api.fit_track_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
    List<Post> findAllByOrderByCreatedAtDesc();
}
