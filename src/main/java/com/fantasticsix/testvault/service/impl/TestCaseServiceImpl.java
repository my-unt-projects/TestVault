package com.fantasticsix.testvault.service.impl;


import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.ModuleRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public TestCase get(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase with id " + id + " not found"));
    }

    @Override
    public TestCase save(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    @Override
    public void delete(Long id) {
        testCaseRepository.deleteById(id);
    }

    @Override
    public TestCase update(TestCase testCase) {
        TestCase savedTestCase = testCaseRepository.findById(testCase.getTestCaseId())
                .orElseThrow(() -> new RuntimeException("TestCase with id " + testCase.getTestCaseId() + " not found"));
        savedTestCase.setAssignedTo(testCase.getAssignedTo());
        savedTestCase.setDescription(testCase.getDescription());
        savedTestCase.setDueDate(testCase.getDueDate());
        savedTestCase.setPriority(testCase.getPriority());
        savedTestCase.setStatus(testCase.getStatus());
        return testCaseRepository.save(savedTestCase);
    }

    @Override
    public List<TestCase> getByModuleId(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        return testCaseRepository.getTestCasesByModule(module);
    }

    @Override
    public List<TestCase> getAll() {
        return testCaseRepository.findAll();
    }
}
