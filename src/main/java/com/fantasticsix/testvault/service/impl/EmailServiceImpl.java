package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendAssignmentNotification(User assignedTo, TestCase testCase, HttpServletRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(assignedTo.getEmail());
            helper.setSubject("New Test Case Assigned: " + testCase.getTitle());
            helper.setFrom(from);

            String htmlContent = getHtmlContent(assignedTo, testCase, request);
            helper.setText(htmlContent, true); // true for HTML

            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getHtmlContent(User assignedTo, TestCase testCase,  HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 10px; border: 1px solid #ddd;">
                    <h2 style="color: #2c3e50;">🔔 Test Case Assignment Notification</h2>
                    <p>Hello <strong>%s</strong>,</p>
                    <p>You have been assigned a new test case. Here are the details:</p>
                    <table style="width: 100%%; border-collapse: collapse;">
                        <tr><td style="padding: 8px; font-weight: bold;">Title:</td><td>%s</td></tr>
                        <tr><td style="padding: 8px; font-weight: bold;">Project:</td><td>%s</td></tr>
                        <tr><td style="padding: 8px; font-weight: bold;">Priority:</td><td>%s</td></tr>
                        <tr><td style="padding: 8px; font-weight: bold;">Due Date:</td><td>%s</td></tr>
                    </table>
                    <p style="margin-top: 20px;">You can view and manage the test case <a href="%s/tests/%d" style="color: #2980b9; text-decoration: none;">here</a>.</p>
                    <p>Thanks,<br/>TestVault Team</p>
                </div>
            </body>
            </html>
        """.formatted(
                assignedTo.getName(),
                testCase.getTitle(),
                testCase.getProject().getProjectName(),
                testCase.getPriority().name(),
                testCase.getDueDate(),
                baseUrl,
                testCase.getTestCaseId()
        );
    }
}
