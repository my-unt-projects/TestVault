package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Notification;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.NotificationRepository;
import com.fantasticsix.testvault.repository.UserRepository;
import com.fantasticsix.testvault.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        Notification savedNotification = notificationRepository.findById(notification.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notification.getNotificationId()));

        savedNotification.setIsRead(notification.getIsRead());
        savedNotification.setMessage(notification.getMessage());
        savedNotification.setSentDate(notification.getSentDate());
        savedNotification.setSentTo(notification.getSentTo());
        return notificationRepository.save(savedNotification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public Notification findNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
    }

    @Override
    public List<Notification> findNotificationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return notificationRepository.findAllBySentTo(user);
    }

    @Override
    public List<Notification> findNotificationsByUserIdAndReadStatus(Long userId, boolean readStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return notificationRepository.findAllBySentToAndIsRead(user, readStatus);
    }
}
