package com.fit_track_api.fit_track_api.service.impl;

import com.cloudinary.Cloudinary;
import com.fit_track_api.fit_track_api.controller.dto.request.CreatePostRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UpdatePostRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetPostByIdResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetPostByUserResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetUserByIdResponseDTO;
import com.fit_track_api.fit_track_api.exceptions.ResourceNotFoundException;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.model.WorkoutPost;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.repository.WorkoutPostRepository;
import com.fit_track_api.fit_track_api.service.WorkoutPostService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class WorkoutPostServiceImpl implements WorkoutPostService {

    private final WorkoutPostRepository workoutPostRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;



    @Override
    public WorkoutPost createPost(CreatePostRequestDTO createPostRequestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<String> imageUrls = new ArrayList<>();
        if (createPostRequestDTO.getImageUrls() != null && !createPostRequestDTO.getImageUrls().isEmpty()) {
            for (MultipartFile image : createPostRequestDTO.getImageUrls()) {
                try {
                    String imageUrl = cloudinary.uploader()
                            .upload(image.getBytes(), Map.of(
                                    "public_id", UUID.randomUUID().toString(),
                                    "folder", "WorkoutPosts"
                            ))
                            .get("url")
                            .toString();
                    imageUrls.add(imageUrl);
                } catch (Exception e) {
                    throw new RuntimeException("Image upload failed", e);
                }
            }
        }

        WorkoutPost post = new WorkoutPost();
        post.setDescription(createPostRequestDTO.getDescription());
        post.setTitle(createPostRequestDTO.getTitle());
        post.setImageUrl(imageUrls);
        post.setUser(user);
        return workoutPostRepository.save(post);
    }

    @Override
    public void updatePost(Long id, UpdatePostRequestDTO updatePostRequestDTO) {
        try {
            WorkoutPost post =workoutPostRepository.findById(id).orElseThrow(()->new RuntimeException("Workut post not found"));

            if (updatePostRequestDTO.getDescription() != null) {
                post.setDescription(updatePostRequestDTO.getDescription());
            }
            if (updatePostRequestDTO.getTitle() != null) {
                post.setTitle(updatePostRequestDTO.getTitle());
            }
            if (updatePostRequestDTO.getImageUrls() != null && !updatePostRequestDTO.getImageUrls().isEmpty()) {
                List<String> uploadedImageUrls = new ArrayList<>();

                for (MultipartFile image : updatePostRequestDTO.getImageUrls()) {
                    String imageUrl = cloudinary.uploader()
                            .upload(image.getBytes(), Map.of(
                                    "public_id", UUID.randomUUID().toString(),
                                    "folder", "WorkoutPosts"))
                            .get("url")
                            .toString();

                    uploadedImageUrls.add(imageUrl);
                }
                post.setImageUrl(uploadedImageUrls);
            }
            workoutPostRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update post with ID: " + id, e);
        }


    }

    @Override
    public void deletePost(Long id) {
        if (!workoutPostRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found");
        }
        workoutPostRepository.deleteById(id);
    }

    @Override
    public GetPostByIdResponseDTO getPostById(Long id) {
        WorkoutPost post = workoutPostRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        GetPostByIdResponseDTO responseDTO = new GetPostByIdResponseDTO();
        responseDTO.setTitle(post.getTitle());
        responseDTO.setDescription(post.getDescription());
        responseDTO.setImageUrls(post.getImageUrl());

        // Convert likedBy (List<User>) to List<GetUserByIdResponseDTO>
        List<GetUserByIdResponseDTO> likedByDTOs = post.getLikedBy().stream().map(user -> {
            GetUserByIdResponseDTO dto = new GetUserByIdResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            return dto;
        }).collect(Collectors.toList());

        responseDTO.setLikedBy(likedByDTOs);

        return responseDTO;
    }


    @Override
    public List<GetPostByUserResponseDTO> getPostsByUser(Long userId) {
        List<WorkoutPost> workoutPosts = workoutPostRepository.findByUserId(userId);

        if (workoutPosts.isEmpty()) {
            throw new RuntimeException("No posts found for this user");
        }

        List<GetPostByUserResponseDTO> responseList = new ArrayList<>();

        for (WorkoutPost post : workoutPosts) {
            GetPostByUserResponseDTO dto = new GetPostByUserResponseDTO();
            dto.setTitle(post.getTitle());
            dto.setDescription(post.getDescription());
            dto.setImageUrls(post.getImageUrl());

            // Convert likedBy (List<User>) to List<GetUserByIdResponseDTO>
            List<GetUserByIdResponseDTO> likedByDTOs = post.getLikedBy().stream().map(user -> {
                GetUserByIdResponseDTO likedUserDTO = new GetUserByIdResponseDTO();
                likedUserDTO.setId(user.getId());
                likedUserDTO.setUsername(user.getUsername());
                likedUserDTO.setEmail(user.getEmail());
                return likedUserDTO;
            }).collect(Collectors.toList());

            dto.setLikedBy(likedByDTOs);
            responseList.add(dto);
        }

        return responseList;
    }


    @Override
    public List<WorkoutPost> getFeedPosts(User user) {
        return user.getFollowing().stream()
                .flatMap(followedUser -> followedUser.getWorkoutPosts().stream())
                .collect(Collectors.toList());
    }


    @Override
    public List<WorkoutPost> getAllPosts() {
        return workoutPostRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional
    public void likePost(Long postId, Long userId) {
        WorkoutPost post = workoutPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!post.getLikedBy().contains(user)) {
            post.getLikedBy().add(user);
            post.setLikedCount(post.getLikedBy().size());
            workoutPostRepository.save(post);
        }
    }


    @Override
    @Transactional
    public void unlikePost(Long postId, Long userId) {
        WorkoutPost post = workoutPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user);
            post.setLikedCount(post.getLikedBy().size());
            workoutPostRepository.save(post);
        }
    }


    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        return false;
    }
}
