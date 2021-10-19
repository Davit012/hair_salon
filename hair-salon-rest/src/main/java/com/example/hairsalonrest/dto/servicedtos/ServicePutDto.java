package com.example.hairsalonrest.dto.servicedtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicePutDto {
    private String name;
    private String description;
    private double price;
}
