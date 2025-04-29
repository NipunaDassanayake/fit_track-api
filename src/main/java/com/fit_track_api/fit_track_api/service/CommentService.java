package com.fit_track_api.fit_track_api.service;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateCommentRequestDTO;
import com.fit_track_api.fit_track_api.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long achievementId, Long userId, CreateCommentRequestDTO dto);
    Comment updateComment(Long commentId, String newContent);
    void deleteComment(Long commentId);
    public List<Comment> getCommentsByAchievement(Long achievementId);
}


