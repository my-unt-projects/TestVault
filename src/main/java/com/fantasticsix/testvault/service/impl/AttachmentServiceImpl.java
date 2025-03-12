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
    public List<Attachment> getAttachmentsByTestCase(Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new RuntimeException("Test case not found"));
        return attachmentRepository.findAttachmentByTestCase(testCase);
    }

    @Override
    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
    }

    @Override
    public Attachment addAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment updateAttachment(Attachment attachment) {
        Attachment savedAttachment = attachmentRepository.findById(attachment.getAttachmentId())
                .orElseThrow(() -> new RuntimeException("Attachment not found. Update operation failed"));
        savedAttachment.setType(attachment.getType());
        savedAttachment.setFilePath(attachment.getFilePath());
        return attachmentRepository.save(savedAttachment);
    }

    @Override
    public void deleteAttachment(Long id) {
        attachmentRepository.deleteById(id);
    }
}
