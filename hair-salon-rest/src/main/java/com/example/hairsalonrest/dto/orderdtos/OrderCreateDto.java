package com.example.hairsalonrest.dto.orderdtos;

import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateDto {
    @NotNull(message = "start time is required")
    @Future
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private User user;
    @NotNull(message = "please input your worker")
    private Worker worker;

}
