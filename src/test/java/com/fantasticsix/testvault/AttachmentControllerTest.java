package com.fantasticsix.testvault;

import com.fantasticsix.testvault.controller.AttachmentController;
import com.fantasticsix.testvault.dto.AttachmentDto;
import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.AttachmentRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.TestCaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttachmentControllerTest {

    @InjectMocks
    private AttachmentController attachmentController;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private TestCaseService testCaseService;

    @Mock
    private TestCaseRepository testCaseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: Upload a valid file
    @Test
    void uploadAttachment_shouldUploadSuccessfully() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test data".getBytes());

        ResponseEntity<Map<String, String>> response = attachmentController.uploadAttachment(file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Upload successful", response.getBody().get("message"));
        assertNotNull(response.getBody().get("uuid"));

        verify(attachmentRepository, times(1)).save(any(Attachment.class));
    }

    // Test: Upload an empty file
    @Test
    void uploadAttachment_shouldReturnBadRequestForEmptyFile() {
        MultipartFile file = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        ResponseEntity<Map<String, String>> response = attachmentController.uploadAttachment(file);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("No file selected", response.getBody().get("message"));

        verify(attachmentRepository, never()).save(any());
    }

    // Test: View an existing attachment
    @Test
    void viewAttachment_shouldReturnAttachmentData() {
        Attachment attachment = new Attachment();
        attachment.setUuid("1234-uuid");
        attachment.setFileName("testfile.txt");
        attachment.setFileType("text/plain");
        attachment.setData("Test content".getBytes());

        when(attachmentRepository.findByUuid("1234-uuid")).thenReturn(Optional.of(attachment));

        ResponseEntity<byte[]> response = attachmentController.viewAttachment("1234-uuid");

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals("Test content".getBytes(), response.getBody());
        assertEquals("application/octet-stream", response.getHeaders().getContentType().toString());
        assertTrue(response.getHeaders().getFirst("Content-Disposition").contains("filename=\"testfile.txt\""));

        verify(attachmentRepository, times(1)).findByUuid("1234-uuid");
    }

    // Test: View a non-existing attachment
    @Test
    void viewAttachment_shouldThrowNotFoundIfNotExist() {
        when(attachmentRepository.findByUuid("not-found")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> attachmentController.viewAttachment("not-found"));
    }

    // Test: Get attachments for a test case
    @Test
    void getTestCaseAttachments_shouldReturnAttachmentDtos() {
        TestCase testCase = new TestCase();
        testCase.setTestCaseId(1L);

        Attachment attachment1 = new Attachment();
        attachment1.setUuid("uuid1");
        attachment1.setFileName("file1.txt");
        attachment1.setFileType("text/plain");

        Attachment attachment2 = new Attachment();
        attachment2.setUuid("uuid2");
        attachment2.setFileName("file2.txt");
        attachment2.setFileType("text/plain");

        when(testCaseRepository.findById(1L)).thenReturn(Optional.of(testCase));
        when(attachmentRepository.getAttachmentsByTestCase(testCase)).thenReturn(Arrays.asList(attachment1, attachment2));

        List<AttachmentDto> result = attachmentController.getTestCaseAttachments(1L);

        assertEquals(2, result.size());
        assertEquals("uuid1", result.get(0).getUuid());
        assertEquals("uuid2", result.get(1).getUuid());

        verify(testCaseRepository, times(1)).findById(1L);
        verify(attachmentRepository, times(1)).getAttachmentsByTestCase(testCase);
    }

    // Test: Get attachments for a non-existing test case
    @Test
    void getTestCaseAttachments_shouldThrowNotFoundIfTestCaseNotExist() {
        when(testCaseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> attachmentController.getTestCaseAttachments(99L));
    }
}