package com.fantasticsix.testvault.service.impl;


import com.fantasticsix.testvault.dto.TestCaseDto;
import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.model.Tag;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.*;
import com.fantasticsix.testvault.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final ModuleRepository moduleRepository;
    private final AttachmentRepository attachmentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TestCase get(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase with id " + id + " not found"));
    }

    @Override
    public TestCase save(TestCase testCase, List<String> attachmentUuids) {
        TestCase savedTestCase = testCaseRepository.save(testCase);
        associateAttachmentsWithTestCase(savedTestCase, attachmentUuids);
        return savedTestCase;
    }

    // Associate attachments with the test case using UUIDs
    private void associateAttachmentsWithTestCase(TestCase testCase, List<String> attachmentUuids) {
        for (String uuid : attachmentUuids) {
            Attachment attachment = attachmentRepository.findByUuid(uuid)
                    .orElseThrow(() -> new RuntimeException("Attachment with uuid " + uuid + " not found"));

            attachment.setTestCase(testCase);
            attachmentRepository.save(attachment);
        }
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

    @Override
    public TestCaseDto getTestCaseById(Long id) {
        return testCaseRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    private TestCaseDto convertToDto(TestCase testCase) {
        return TestCaseDto.builder()
                .testCaseId(testCase.getTestCaseId())
                .title(testCase.getTitle())
                .description(testCase.getDescription())
                .priority(String.valueOf(testCase.getPriority()))
                .status(String.valueOf(testCase.getStatus()))
                .creationDate(testCase.getCreationDate())
                .dueDate(testCase.getDueDate())
                .assignedToEmail(testCase.getAssignedTo() != null ? testCase.getAssignedTo().getEmail() : null)
                .moduleId(testCase.getModule() != null ? testCase.getModule().getModuleId() : null)
                .projectId(testCase.getProject() != null ? testCase.getProject().getId() : null)
                .moduleName(testCase.getModule() != null ? testCase.getModule().getModuleName() : null)
                .tagIds(testCase.getTags().stream().map(Tag::getTagId).collect(Collectors.toList()))
                .build();
    }

    public TestCase convertFromDto(TestCaseDto dto) {
        TestCase testCase = new TestCase();
        testCase.setTestCaseId(dto.getTestCaseId());
        testCase.setTitle(dto.getTitle());
        testCase.setDescription(dto.getDescription());
        testCase.setPriority(TestCase.Priority.valueOf(dto.getPriority()));
        testCase.setStatus(TestCase.Status.valueOf(dto.getStatus()));
        testCase.setCreationDate(dto.getCreationDate() != null ? dto.getCreationDate() : new Date());
        testCase.setDueDate(dto.getDueDate());

        if (dto.getAssignedToEmail() != null) {
            userRepository.findByEmailIgnoreCase(dto.getAssignedToEmail()).ifPresent(testCase::setAssignedTo);
        }

        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(dto.getTagIds());
            testCase.setTags(tags);
        }

        if (dto.getModuleId() != null) {
            moduleRepository.findById(dto.getModuleId()).ifPresent(testCase::setModule);
        }

        if (dto.getProjectId() != null) {
            projectRepository.findById(dto.getProjectId()).ifPresent(testCase::setProject);
        }

        return testCase;
    }
}
