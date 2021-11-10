package com.example.hairsalonrest.service;

import com.example.hairsalonrest.repository.FeedbackRepository;
import com.hairsaloncommon.model.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;


    @Test
    public void findAll() {

        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.save(Mockito.any())).thenReturn(feedback);
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
        Feedback addFeedback = feedbackService.addFeedback(feedback);

        assertThat(addFeedback.getId() == feedback.getId());
        //assertEquals(1, feedbackRepository.findAll().size());
    }

    @Test
    public void findById() {
        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.save(Mockito.any())).thenReturn(feedback);
        Feedback save = feedbackRepository.save(feedback);
        Optional<Feedback> foundFeedback = feedbackService.findById(feedback.getId());
        assertEquals(foundFeedback.get().getId(), save.getId());
    }

    @Test
    public void editFeedback() {
        Feedback feedback = Feedback.builder()
                .id(1)
                .rate(5)
                .message("Feedback")
                .build();

        when(feedbackRepository.save(Mockito.any())).thenReturn(feedback);
        Feedback save = feedbackRepository.save(feedback);
        save.setMessage("newFirstName");
        Feedback editFeedback = feedbackService.editFeedback(save.getId(), save);
        assertEquals(editFeedback.getMessage(), is("newFirstName"));
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
