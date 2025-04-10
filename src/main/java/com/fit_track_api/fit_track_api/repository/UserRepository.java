package com.fit_track_api.fit_track_api.repository;
import com.fit_track_api.fit_track_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByFollowingContaining(User user);
    List<User> findByFollowersContaining(User user);
    // Add this line:
    boolean existsByEmail(String email);

    // You might also want to add:
    boolean existsByUsername(String username);
}