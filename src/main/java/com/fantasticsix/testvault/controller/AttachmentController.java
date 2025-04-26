package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.AttachmentDto;
import com.fantasticsix.testvault.dto.TestCaseDto;
import com.fantasticsix.testvault.model.Attachment;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.AttachmentRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.TestCaseService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attachments")
@Slf4j
public class AttachmentController {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private TestCaseRepository testCaseRepository;

    // Upload Attachment
    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<Map<String, String>> uploadAttachment(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                response.put("message", "No file selected");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Create a new attachment instance
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());

            byte[] fileBytes = file.getBytes();
            log.info("File bytes length: " + fileBytes.length);

            attachment.setData(fileBytes);
            // UUID will be automatically generated when the object is created
            attachmentRepository.save(attachment);

            response.put("uuid", attachment.getUuid());
            response.put("message", "Upload successful");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("message", "Upload failed!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Utility method to remove characters not allowed in HTTP headers
    private String sanitizeFilename(String filename) {
        // Replace non-ASCII characters (e.g., U+202F) with a space or remove them
        return filename.replaceAll("[^\\x20-\\x7E]", " ");
    }

    @GetMapping("/{uuid}/view")
    @Transactional
    public ResponseEntity<byte[]> viewAttachment(@PathVariable String uuid) {
        Attachment attachment = attachmentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + sanitizeFilename(attachment.getFileName()) + "\"")
                .body(attachment.getData());
    }


    @GetMapping("/testcases/{testCaseId}")
    @Transactional
    public List<AttachmentDto> getTestCaseAttachments(@PathVariable Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Attachment> attachments = attachmentRepository.getAttachmentsByTestCase(testCase);

        return attachments.stream()
                .map(att -> new AttachmentDto(
                        att.getUuid(),
                        att.getFileName(),
                        att.getFileType(),
                        "/api/attachments/" + att.getUuid() + "/view"
                ))
                .collect(Collectors.toList());
    }
}