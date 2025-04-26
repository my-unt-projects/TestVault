package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.dto.TestCaseDto;
import com.fantasticsix.testvault.model.TestCase;

import java.util.List;

public interface TestCaseService {
    TestCase get(Long id);
    TestCase save(TestCase testCase, List<String> attachmentUuids);
    void delete(Long id);
    TestCase update(TestCase testCase);
    List<TestCase> getByModuleId(Long moduleId);
    List<TestCase> getAll();
    TestCaseDto getTestCaseById(Long id);
    void deleteTestCaseById(Long id);

    List<TestCase> getFilteredTestCases(Long projectId, Long moduleId, String status, String assignedTo);
}