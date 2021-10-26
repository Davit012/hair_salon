package com.example.hairsalonrest.endpoint;

import com.example.hairsalonrest.dto.feedbackdtos.CreateFeedbackDto;
import com.example.hairsalonrest.dto.feedbackdtos.FeedbackDto;
import com.example.hairsalonrest.dto.feedbackdtos.FeedbackPutDto;
import com.example.hairsalonrest.service.FeedbackService;
import com.hairsaloncommon.model.Feedback;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feedbacks")
public class FeedbackEndpoint {
    private final FeedbackService feedbackService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        List<Feedback> all = feedbackService.findAll();
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        if (all.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        for (Feedback feedback : all) {
            FeedbackDto feedbackDto = mapper.map(feedback, FeedbackDto.class);
            feedbackDtos.add(feedbackDto);
        }
        return ResponseEntity.ok(feedbackDtos);
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> addFeedback(@RequestBody CreateFeedbackDto feedback) {
        Feedback byId = feedbackService.addFeedback(mapper.map(feedback, Feedback.class));
        if (byId.getId() != 0) {
            return ResponseEntity.ok(mapper.map(byId, FeedbackDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedback(@PathVariable("id") int id) {
        Optional<Feedback> byId = feedbackService.findById(id);
        return byId.map(feedback ->
                ResponseEntity.ok(mapper.map(feedback, FeedbackDto.class))).orElseGet(() ->
                ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDto> updateFeedback(@PathVariable(name = "id") int id, @RequestBody FeedbackPutDto feedback) {
        if (feedbackService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Feedback feedbackFromDb = feedbackService.editFeedback(id, mapper.map(feedback, Feedback.class));
        return ResponseEntity.ok(mapper.map(feedbackFromDb, FeedbackDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable(name = "id") int id) {
        if (feedbackService.findById(id).isPresent()) {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}