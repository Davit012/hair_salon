package com.hairsaloncommon.dto.servicedtos;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private List<Worker> workers;
}
