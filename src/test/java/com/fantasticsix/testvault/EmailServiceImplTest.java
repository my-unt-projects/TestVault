package com.fantasticsix.testvault;

import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.service.impl.EmailServiceImpl;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Field;
import java.util.Date;

import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    private JavaMailSender mailSender;
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailServiceImpl(mailSender);
    }

    @Test
    void sendAssignmentNotification_shouldSendEmailSuccessfully() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        HttpServletRequest request = mock(HttpServletRequest.class);
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("John Doe");

        Project project = new Project();
        project.setProjectName("Test Project");

        TestCase testCase = new TestCase();
        testCase.setTitle("Sample Test Case");
        testCase.setProject(project);
        testCase.setPriority(TestCase.Priority.HIGH);
        testCase.setDueDate(new Date());
        testCase.setTestCaseId(123L);

        Field fromField = EmailServiceImpl.class.getDeclaredField("from");
        fromField.setAccessible(true);
        fromField.set(emailService, "noreply@testvault.com");

        // Call the method
        emailService.sendAssignmentNotification(user, testCase, request);

        // Verify that send was called
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void sendAssignmentNotification_shouldHandleException() throws Exception {
        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Simulated Mail Error"));

        HttpServletRequest request = mock(HttpServletRequest.class);
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("John Doe");

        Project project = new Project();
        project.setProjectName("Test Project");

        TestCase testCase = new TestCase();
        testCase.setTitle("Sample Test Case");
        testCase.setProject(project);
        testCase.setPriority(TestCase.Priority.HIGH);
        testCase.setDueDate(new Date());
        testCase.setTestCaseId(123L);

        // Call the method (should catch exception internally)
        emailService.sendAssignmentNotification(user, testCase, request);

        // Even on exception, it should not throw outside
        verify(mailSender, times(1)).createMimeMessage();
    }
}