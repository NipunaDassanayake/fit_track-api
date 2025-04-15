package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.CreatePostRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdatePostRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetPostByIdResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetPostByUserResponseDTO;
import com.fit_track_api.fit_track_api.model.WorkoutPost;
import com.fit_track_api.fit_track_api.service.WorkoutPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workoutPost")

public class WorkoutPostController {

    private WorkoutPostService workoutPostService;

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<String> createPost(@ModelAttribute CreatePostRequestDTO createPostRequestDTO, @PathVariable Long userId){
        WorkoutPost createdPost = workoutPostService.createPost(createPostRequestDTO,userId);
        return ResponseEntity.ok("Post Created Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id,@ModelAttribute UpdatePostRequestDTO updatePostRequestDTO){
        workoutPostService.updatePost(id,updatePostRequestDTO);
        return ResponseEntity.ok("Workout Post updated successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(Long id){
        workoutPostService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostByIdResponseDTO> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(workoutPostService.getPostById(id));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetPostByUserResponseDTO>> getPostsByUser(@PathVariable Long userId) {
        List<GetPostByUserResponseDTO> posts = workoutPostService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public List<WorkoutPost> getAllPosts(){
        return workoutPostService.getAllPosts();
    }

    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId,
            @PathVariable Long userId) {
        workoutPostService.likePost(postId, userId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping("/{postId}/unlike/{userId}")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long postId,
            @PathVariable Long userId) {
        workoutPostService.unlikePost(postId, userId);
        return ResponseEntity.ok("Post unliked successfully");
    }
}
