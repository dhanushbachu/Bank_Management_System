package com.bank.management_system.controller;

import com.bank.management_system.model.Feedback;
import com.bank.management_system.model.FeedbackStatus;
import com.bank.management_system.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.FeatureDescriptor;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        Feedback createdFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.ok(createdFeedback);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long feedbackId){
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(){
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByCustomerId(@PathVariable Long customerId){
        List<Feedback> feedbacks = feedbackService.getFeedbacksByCustomerId(customerId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Feedback>> getFeedbacksByStatus(@RequestBody FeedbackStatus feedbackStatus){
        List<Feedback> feedbacks = feedbackService.getFeedbacksByStatus(feedbackStatus);
        return ResponseEntity.ok(feedbacks);
    }

    @PatchMapping("/{feedbackId}/status")
    public ResponseEntity<Feedback> updateFeedbackStatus(@PathVariable Long feedbackId,@RequestParam FeedbackStatus feedbackStatus,@RequestParam Long resolvedBy){
        Feedback feedback = feedbackService.updateFeedbackStatus(feedbackId,feedbackStatus,resolvedBy);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/resolution/note")
    public ResponseEntity<Feedback> addResolutionNote(@PathVariable Long feedbackId,@PathVariable String resolutionNote,@PathVariable Long resolvedBy){
        Feedback feedback = feedbackService.addResolutionNote(feedbackId,resolutionNote,resolvedBy);
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long feedbackId){
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }
}
