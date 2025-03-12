package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> getAttachmentsByTestCase(TestCase testCase);
}
