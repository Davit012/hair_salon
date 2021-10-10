package com.hairsaloncommon.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
    private Date startDatetime;
    private Date endDatetime;
    @ManyToOne
    private WorkerService workerService;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
