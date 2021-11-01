package com.hairsaloncommon.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ordering")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    @OneToMany
    private List<Service> services;
    @ManyToOne
    private User user;
    private Boolean isDeleted;
    @ManyToOne
    private Worker worker;

}
