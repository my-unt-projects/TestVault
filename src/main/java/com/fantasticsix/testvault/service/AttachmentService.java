package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.Attachment;

import java.util.List;

public interface AttachmentService {
    List<Attachment> getAttachmentsByTestCase(Long testCaseId);
    Attachment getAttachment(Long id);
    Attachment addAttachment(Attachment attachment);
    Attachment updateAttachment(Attachment attachment);
    void deleteAttachment(Long id);
}
