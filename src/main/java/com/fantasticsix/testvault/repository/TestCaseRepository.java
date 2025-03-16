package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    Optional<TestCase> findByName(String name);
}