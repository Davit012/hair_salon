package com.hairsaloncommon.dto.feedbackdtos;

import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {

    private User user;
    private Worker worker;
    private int rate;
    private String message;
}
