package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateUserRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UserUpdateRequestDTO;
import com.fit_track_api.fit_track_api.model.User;

import java.util.List;

public interface UserService {
    User registerUser(CreateUserRequestDTO createUserRequestDTO);
    User updateUser(Long id, UserUpdateRequestDTO userUpdateRequestDTO);
    void deleteUser(Long id);
    User getUserById(Long id);
    List<User> getAllUsers();
    void followUser(Long followerId, Long followedId);
    void unfollowUser(Long followerId, Long followedId);
    List<User> getFollowing(Long userId);
    List<User> getFollowers(Long userId);
}
