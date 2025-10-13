package com.bank.management_system.service.impl;


import com.bank.management_system.model.Feedback;
import com.bank.management_system.model.FeedbackStatus;
import com.bank.management_system.repository.FeedbackRepository;
import com.bank.management_system.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback createFeedback(Feedback feedback){
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback getFeedbackById(Long feedbackId){
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(()->new RuntimeException("Feedback not found with id: "+ feedbackId));
    }

    @Override
    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> getFeedbacksByCustomerId(Long customerId){
        return feedbackRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public List<Feedback> getFeedbacksByStatus(FeedbackStatus status){
        return feedbackRepository.findByStatus(status);
    }

    @Override
    public Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatus status, Long resolvedBy){
        Feedback feedback = getFeedbackById(feedbackId);
        feedback.setStatus(status);
        if (status == FeedbackStatus.RESOLVED || status == FeedbackStatus.CLOSED) {
            feedback.setResolvedDate(LocalDateTime.now());
        }
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback addResolutionNote(Long feedbackId, String resolutionNote, Long resolvedBy){
        Feedback feedback = getFeedbackById(feedbackId);
        // Assuming you have a resolutionNote field in Feedback entity
        // feedback.setResolutionNote(resolutionNote);
        feedback.setStatus(FeedbackStatus.RESOLVED);
        feedback.setResolvedDate(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
