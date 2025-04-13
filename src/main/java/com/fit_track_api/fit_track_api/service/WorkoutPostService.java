package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.CreatePostRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdatePostRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetPostByIdResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetPostByUserResponseDTO;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.WorkoutPost;

import java.util.List;

public interface WorkoutPostService {
    WorkoutPost createPost(CreatePostRequestDTO createPostRequestDTO, Long userId);
    void updatePost(Long id, UpdatePostRequestDTO updatePostRequestDTO);
    void deletePost(Long id);
    GetPostByIdResponseDTO getPostById(Long id);
    List<GetPostByUserResponseDTO> getPostsByUser(Long userId);
    List<WorkoutPost> getFeedPosts(User user);
    List<WorkoutPost> getAllPosts();
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    boolean isPostLikedByUser(Long postId, Long userId);
}
