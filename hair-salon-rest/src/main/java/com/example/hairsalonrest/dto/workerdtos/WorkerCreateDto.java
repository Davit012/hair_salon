package com.example.hairsalonrest.dto.workerdtos;

import com.example.hairsalonrest.dto.servicedtos.ServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@NotNull
@NotEmpty
public class WorkerCreateDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Surname is required")
    private String surname;
    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;
    @NotEmpty(message = "Services is required")
    private List<ServiceDto> services;
}
