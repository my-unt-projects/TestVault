package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;

    @Override
    public List<TestCase> listOfAllTestCases() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase createTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    @Override
    public TestCase updateTestCase(Long id, TestCase testCaseDetails) {
        TestCase testCase = testCaseRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Test case not found"));

        testCase.setTitle(testCaseDetails.getTitle());
        testCase.setDescription(testCaseDetails.getDescription());
        testCase.setPriority(testCaseDetails.getPriority());
        testCase.setStatus(testCaseDetails.getStatus());
        testCase.setCreationDate(testCaseDetails.getCreationDate());
        testCase.setDueDate(testCaseDetails.getDueDate());
        testCase.setAssignedTo(testCaseDetails.getAssignedTo());
        testCase.setTags(testCaseDetails.getTags());
        testCase.setAttachments(testCaseDetails.getAttachments());
        testCase.setComments(testCaseDetails.getComments());
        testCase.setModule(testCaseDetails.getModule());

        return testCaseRepository.save(testCase);
    }

    @Override
    public void deleteTestCaseById(Long id) {
        TestCase testCase = testCaseRepository.getByTestCaseId(id)
                .orElseThrow(() -> new RuntimeException("Test case not found"));
        testCaseRepository.delete(testCase);
    }
}