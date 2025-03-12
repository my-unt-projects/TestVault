package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Comment;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.CommentRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Comment savedComment = commentRepository.findById(comment.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        savedComment.setContent(comment.getContent());
        return commentRepository.save(savedComment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByTestCase(Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new RuntimeException("TestCase not found"));
        return commentRepository.findByTestCase(testCase);
    }
}
