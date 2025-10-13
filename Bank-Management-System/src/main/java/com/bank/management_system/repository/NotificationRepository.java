package com.bank.management_system.repository;

import com.bank.management_system.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long> {
    List<Notification> findByReceiverUserId(Long receiverUserId);
    List<Notification> findByReceiverUserIdAndReadFalse(Long receiverId);
    List<Notification> findBySenderUserId(Long senderId);
}
