package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.Attachment;

import java.util.List;

public interface AttachmentService {
    List<Attachment> getAttachmentsByTestCaseId(Long testCaseId);
    Attachment getAttachment(Long id);
    void deleteAttachment(Long id);
}