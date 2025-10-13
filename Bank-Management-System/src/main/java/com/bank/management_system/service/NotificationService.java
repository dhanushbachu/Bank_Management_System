package com.bank.management_system.service;

import com.bank.management_system.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Notification getNotificationById(Long notificationId);
    List<Notification> getNotificationsByReceiverId(Long receiverId);
    List<Notification> getUnreadNotificationsByReceiverId(Long receiverId);
    List<Notification> getNotificationsBySenderId(Long senderId);
    Notification markAsRead(Long notificationId);
    int getUnreadNotificationsCount(Long customerId);
    void deleteNotification(Long notificationId);
}
