package com.fantasticsix.testvault.repository;

import com.fantasticsix.testvault.model.Notification;
import com.fantasticsix.testvault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllBySentTo(User sentTo);

    List<Notification> findAllBySentToAndIsRead(User sentTo, Boolean isRead);
}
