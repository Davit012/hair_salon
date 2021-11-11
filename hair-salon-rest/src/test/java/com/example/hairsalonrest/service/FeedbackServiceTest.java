package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.example.hairsalonrest.repository.FeedbackRepository;
import com.hairsaloncommon.model.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class FeedbackServiceTest {

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackService feedbackService;


    @Test
    public void findAll() {

        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback));
        List<Feedback> all = feedbackService.findAll();
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void addFeedbackTest() {
        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.save(Mockito.any())).thenReturn(feedback);
        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback));
        Feedback addFeedback = feedbackService.addFeedback(feedback);

        assertEquals(addFeedback.getId(), feedback.getId());
        assertEquals(1, feedbackRepository.findAll().size());
    }

    @Test
    public void findById() {
        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
        Optional<Feedback> foundFeedback = feedbackService.findById(feedback.getId());
        assertEquals(foundFeedback.get().getId(), feedback.getId());
    }

    @Test
    public void editFeedback() {
        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
        when(feedbackRepository.save(Mockito.any())).thenReturn(feedback);
        Feedback save = feedbackRepository.save(feedback);
        save.setMessage("newFirstName");
        Feedback editFeedback = feedbackService.editFeedback(feedback.getId(), save);
        assertEquals(editFeedback.getMessage(), "newFirstName");
    }

    @Test
    public void deleteFeedbackTest() {
        int id = 6;
        Feedback feedback = Feedback.builder()
                .id(id)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.findById(id)).thenReturn(Optional.of(feedback));
        feedbackService.deleteFeedback(id);
        verify(feedbackRepository).deleteById(id);
    }


}
