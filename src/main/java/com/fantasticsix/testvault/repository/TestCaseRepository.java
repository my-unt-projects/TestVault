package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fantasticsix.testvault.model.Module;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    @Query("SELECT t FROM TestCase t JOIN t.tags tag WHERE tag.tagId = :tagId")
    List<TestCase> findByTagId(@Param("tagId") Long tagId);

    List<TestCase> getTestCasesByModule(Module module);
}
