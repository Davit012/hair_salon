package com.example.hairsalonrest.dto.orderdtos;

import com.hairsaloncommon.model.Service;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateDto {

    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private List<Service> services;
    private User user;
    private Worker worker;

}
