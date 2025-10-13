package com.bank.management_system.service;

import com.bank.management_system.model.Feedback;
import com.bank.management_system.model.FeedbackStatus;

import java.beans.FeatureDescriptor;
import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(Feedback feedback);
    Feedback getFeedbackById(Long feedbackId);
    List<Feedback> getAllFeedbacks();
    List<Feedback> getFeedbacksByCustomerId(Long customerId);
    List<Feedback> getFeedbacksByStatus(FeedbackStatus status);
    Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatus status, Long resolvedBy);
    Feedback addResolutionNote(Long feedbackId, String resolutionNote, Long resolvedBy);
    void deleteFeedback(Long feedbackId);
}
