package com.example.hairsalonrest.dto.feedbackdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackPutDto {

    private int rate;
    private String message;
}
