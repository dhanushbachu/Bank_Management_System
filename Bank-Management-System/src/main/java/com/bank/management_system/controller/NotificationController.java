package com.bank.management_system.controller;

import com.bank.management_system.model.Notification;
import com.bank.management_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){
        Notification createdNotification = notificationService.createNotification(notification);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId){
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/customer/{customerId}")
    public  ResponseEntity<List<Notification>> getNotificationsByCustomerId(@PathVariable Long customerId){
        List<Notification> notifications = notificationService.getNotificationsByReceiverId(customerId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/customer/{customerId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsByCustomerId(@PathVariable Long customerId){
        List<Notification> notifications = notificationService.getUnreadNotificationsByReceiverId(customerId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Notification>> getNotificationsBySenderId(@PathVariable Long senderId){
        List<Notification> notifications = notificationService.getNotificationsBySenderId(senderId);
        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long notificationId){
        Notification notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/customer/{customerId}/unread-count")
    public ResponseEntity<Integer> getUnreadNotificationsCount(@PathVariable Long customerId){
        int count = notificationService.getUnreadNotificationsCount(customerId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId){
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
 }
