package com.bank.management_system.repository;

import com.bank.management_system.model.Feedback;
import com.bank.management_system.model.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStatus(FeedbackStatus status);
    List<Feedback> findByCustomer_CustomerId(Long customerId);
    List<Feedback> findByResolvedBy_UserId(Long userId);

}
