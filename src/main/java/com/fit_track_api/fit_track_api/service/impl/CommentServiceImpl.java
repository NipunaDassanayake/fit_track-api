package com.fit_track_api.fit_track_api.service.impl;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetAllUsersResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetCommentResponseDTO;
import com.fit_track_api.fit_track_api.model.Achievement;
import com.fit_track_api.fit_track_api.model.Comment;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.repository.AchievementRepository;
import com.fit_track_api.fit_track_api.repository.CommentRepository;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private AchievementRepository achievementRepository;
    private UserRepository userRepository;

    @Override
    public Comment addComment(Long achievementId, Long userId, CreateCommentRequestDTO dto) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAchievement(achievement);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<GetCommentResponseDTO> getCommentsByAchievement(Long achievementId) {
        List<Comment> comments = commentRepository.findByAchievementId(achievementId);
        return comments.stream()
                .map(comment -> {
                    GetCommentResponseDTO dto = new GetCommentResponseDTO();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setCreatedAt(comment.getCreatedAt());
                    dto.setUserId(comment.getUser().getId());
                    dto.setUsername(comment.getUser().getUsername());
                    dto.setAchievementId(comment.getAchievement().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
