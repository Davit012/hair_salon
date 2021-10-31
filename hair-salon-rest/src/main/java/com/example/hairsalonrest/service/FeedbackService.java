package com.example.hairsalonrest.service;

import com.hairsaloncommon.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    List<Feedback> findAll();

    Feedback addFeedback(Feedback feedback);

    Optional<Feedback> findById(int id);

    Feedback editFeedback(int id, Feedback feedback);

    void deleteFeedback(int id);
}
