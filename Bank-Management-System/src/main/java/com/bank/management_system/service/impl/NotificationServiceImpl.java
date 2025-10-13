package com.bank.management_system.service.impl;

import com.bank.management_system.model.Notification;
import com.bank.management_system.repository.NotificationRepository;
import com.bank.management_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
    }

    @Override
    public List<Notification> getNotificationsByReceiverId(Long receiverId) {
        return notificationRepository.findByReceiverUserId(receiverId);
    }

    @Override
    public List<Notification> getUnreadNotificationsByReceiverId(Long receiverId) {
        return notificationRepository.findByReceiverUserIdAndReadFalse(receiverId);
    }

    @Override
    public List<Notification> getNotificationsBySenderId(Long senderId) {
        return notificationRepository.findBySenderUserId(senderId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @Override
    public int getUnreadNotificationsCount(Long customerId) {
        return notificationRepository.findByReceiverUserIdAndReadFalse(customerId).size();
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}