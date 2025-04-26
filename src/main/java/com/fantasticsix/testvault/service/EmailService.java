package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface EmailService {
    void sendAssignmentNotification(User assignedTo, TestCase testCase, HttpServletRequest request);
}
