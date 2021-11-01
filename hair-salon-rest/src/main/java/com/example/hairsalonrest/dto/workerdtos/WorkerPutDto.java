package com.example.hairsalonrest.dto.workerdtos;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WorkerPutDto {
    private String name;
    private String surname;
    private String phoneNumber;
}
