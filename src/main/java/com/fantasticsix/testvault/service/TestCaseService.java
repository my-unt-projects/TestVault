package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.TestCase;

import java.util.List;

public interface TestCaseService {
    TestCase get(Long id);
    TestCase save(TestCase testCase);
    void delete(Long id);
    TestCase update(TestCase testCase);
    List<TestCase> getByModuleId(Long moduleId);
}