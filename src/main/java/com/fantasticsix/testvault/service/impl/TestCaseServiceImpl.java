package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.model.Tag;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.ModuleRepository;
import com.fantasticsix.testvault.repository.TagRepository;
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
    public TestCase getTestCase(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase with id " + id + " not found"));
    }

    @Override
    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    @Override
    public void deleteTestCase(Long id) {
        testCaseRepository.deleteById(id);
    }

    @Override
    public TestCase updateTestCase(TestCase testCase) {
        TestCase savedTestCase = testCaseRepository.findById(testCase.getTestCaseId())
                .orElseThrow(() -> new RuntimeException("TestCase with id " + testCase.getTestCaseId() + " not found"));
        savedTestCase.setAssignedTo(testCase.getAssignedTo());
        savedTestCase.setComments(testCase.getComments());
        savedTestCase.setDescription(testCase.getDescription());
        savedTestCase.setAttachments(testCase.getAttachments());
        savedTestCase.setDueDate(testCase.getDueDate());
        savedTestCase.setPriority(testCase.getPriority());
        savedTestCase.setTags(testCase.getTags());
        savedTestCase.setStatus(testCase.getStatus());
        return testCaseRepository.save(savedTestCase);
    }

    @Override
    public List<TestCase> getTestCasesByModuleId(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        return testCaseRepository.getTestCasesByModule(module);
    }

    @Override
    public List<TestCase> getTestCasesByTagId(Long tagId) {
        return testCaseRepository.findByTagId(tagId);
    }
}
