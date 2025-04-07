package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.Comment;
import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByTestCase(TestCase testCase);
}