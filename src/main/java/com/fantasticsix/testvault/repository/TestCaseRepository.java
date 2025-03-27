package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    Optional<TestCase> getByTestCaseId(Long testCaseId);
}