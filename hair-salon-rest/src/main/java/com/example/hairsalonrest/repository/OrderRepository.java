package com.example.hairsalonrest.repository;

import com.hairsaloncommon.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getAllByUser_Id(int id);
    List<Order> findAllByWorker_Id(int id);

}
