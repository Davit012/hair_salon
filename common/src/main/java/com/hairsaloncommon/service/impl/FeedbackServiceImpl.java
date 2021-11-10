package com.hairsaloncommon.service.impl;

import com.hairsaloncommon.model.Feedback;
import com.hairsaloncommon.repository.FeedbackRepository;
import com.hairsaloncommon.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper mapper;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback addFeedback(Feedback feedback) {
        if (feedback.getRate() >= 0 && feedback.getRate() <= 5) {
            return feedbackRepository.save(feedback);
        }
        return feedback;
    }

    @Override
    public Optional<Feedback> findById(int id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public Feedback editFeedback(int id, Feedback feedback) {
        Feedback byId = feedbackRepository.findById(id).get();
        feedback.setId(id);
        mapper.map(feedback, byId);
        return addFeedback(feedback);
    }

    @Override
    public void deleteFeedback(int id) {
        feedbackRepository.deleteById(id);
    }
}

