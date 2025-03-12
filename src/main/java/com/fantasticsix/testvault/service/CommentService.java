package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    Comment getCommentById(Long id);
    void deleteComment(Long id);
    List<Comment> getCommentsByTestCase(Long testCaseId);
}