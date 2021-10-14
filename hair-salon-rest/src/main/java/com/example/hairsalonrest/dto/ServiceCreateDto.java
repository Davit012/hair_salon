package com.example.hairsalonrest.dto;

import com.hairsaloncommon.model.Worker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceCreateDto {
    private String name;
    private String description;
    private double price;
    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    private List<Worker> workers;
}
