package com.hairsaloncommon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "worker_service")
public class WorkerService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
