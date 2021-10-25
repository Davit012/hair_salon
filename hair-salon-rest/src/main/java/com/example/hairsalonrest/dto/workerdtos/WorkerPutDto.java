package com.example.hairsalonrest.dto.workerdtos;

import com.hairsaloncommon.model.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WorkerPutDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private List<Service> services;
}
