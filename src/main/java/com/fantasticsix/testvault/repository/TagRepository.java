package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t JOIN t.testCases tc WHERE tc.testCaseId = :testCaseId")
    List<Tag> findByTestCaseId(@Param("testCaseId") Long testCaseId);
}
