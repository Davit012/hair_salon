package com.example.hairsalonrest.dto.orderdtos;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPutDto {

    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private Worker worker;

}
