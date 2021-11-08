package com.example.hairsalonrest.dto.orderdtos;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPutDto {
    @NotNull(message = "Rate is required")
    @Future
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    @NotNull(message = "please input your worker")
    private Worker worker;

}
