package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Notification updateNotification(Notification notification);
    void deleteNotification(Long notificationId);
    Notification findNotificationById(Long notificationId);
    //Todo discuss method on pulling all notifications
    List<Notification> findNotificationsByUserId(Long userId);
    List<Notification> findNotificationsByUserIdAndReadStatus(Long userId, boolean readStatus);
}
