package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.TestCase;
import java.util.List;

public interface TestCaseService {
    // List all test cases
    List<TestCase> listOfAllTestCases();

    // Create test case
    TestCase createTestCase(TestCase testCase);

    // Update test case
    TestCase updateTestCase(Long id, TestCase testCaseDetails);

    // Delete test case by title
    void deleteTestCaseByTitle(String title);
}