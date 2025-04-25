package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;

public interface EmailService {
    void sendAssignmentNotification(User assignedTo, TestCase testCase);
}
