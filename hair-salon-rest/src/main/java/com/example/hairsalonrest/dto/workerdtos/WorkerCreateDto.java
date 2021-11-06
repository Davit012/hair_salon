package com.example.hairsalonrest.dto.workerdtos;

import com.example.hairsalonrest.dto.servicedtos.ServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WorkerCreateDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private List<ServiceDto> services;
}
