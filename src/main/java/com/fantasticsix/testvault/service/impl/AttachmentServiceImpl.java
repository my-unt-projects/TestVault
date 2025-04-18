package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.AttachmentRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    public List<Attachment> getAttachmentsByTestCaseId(Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new RuntimeException("Test case not found with id: " + testCaseId));
        return attachmentRepository.getAttachmentsByTestCase(testCase);
    }

    @Override
    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found with id: " + id));
    }


    @Override
    public void deleteAttachment(Long id) {
        attachmentRepository.deleteById(id);
    }
}