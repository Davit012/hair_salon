package com.example.hairsalonrest.dto.workerdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WorkerDto {
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
}
