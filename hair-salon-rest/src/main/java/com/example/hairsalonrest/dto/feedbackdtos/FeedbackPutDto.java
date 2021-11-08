package com.example.hairsalonrest.dto.feedbackdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackPutDto {
    @NotEmpty(message = "Rate is required")
    @Min(0)
    @Max(5)
    private int rate;

    private String message;
}
