package com.fit_track_api.fit_track_api.repository;

import com.fit_track_api.fit_track_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
