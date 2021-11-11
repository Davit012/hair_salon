package com.example.hairsalonrest.endpoint;

import com.hairsaloncommon.dto.feedbackdtos.CreateFeedbackDto;
import com.hairsaloncommon.dto.feedbackdtos.FeedbackDto;
import com.hairsaloncommon.dto.feedbackdtos.FeedbackPutDto;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.Feedback;
import com.hairsaloncommon.service.FeedbackService;
import com.hairsaloncommon.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feedbacks")
public class FeedbackEndpoint {
    private final FeedbackService feedbackService;
    private final WorkerService workerService;
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

    @PostMapping("/{id}")
    public ResponseEntity<FeedbackDto> addFeedback(@PathVariable(name = "id") int id, @RequestBody @Valid CreateFeedbackDto feedback, @AuthenticationPrincipal CurrentUser currentUser) {
        feedback.setUser(currentUser.getUser());
        feedback.setWorker(workerService.findWorkerById(id));
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
    public ResponseEntity<FeedbackDto> updateFeedback(@PathVariable(name = "id") int id, @RequestBody @Valid FeedbackPutDto feedback) {
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