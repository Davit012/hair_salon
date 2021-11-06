package com.example.hairsalonrest.dto.orderdtos;

import com.hairsaloncommon.model.User;
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
public class OrderCreateDto {

    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private User user;
    private Worker worker;

}
