package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.TestCase;

import java.util.List;

public interface TestCaseService {
    TestCase getTestCase(Long id);
    TestCase saveTestCase(TestCase testCase);
    void deleteTestCase(Long id);
    TestCase updateTestCase(TestCase testCase);
    List<TestCase> getTestCasesByModuleId(Long moduleId);
    List<TestCase> getTestCasesByTagId(Long tagId);
}
