package com.example.hairsalonrest.dto.servicedtos;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceCreateDto {

    @NotEmpty(message = "name is required")
    private String name;
    @NotEmpty(message = "description is required")
    private String description;
    @NotNull
    @Min(5)
    private int duration;
    @NotNull
    @DecimalMin(value = "0.0")
    private double price;
    @NotEmpty
    private List<Worker> workers;
}
