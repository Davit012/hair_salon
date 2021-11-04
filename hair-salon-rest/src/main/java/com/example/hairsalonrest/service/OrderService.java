package com.example.hairsalonrest.service;

import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll(CurrentUser currentUser);

    Order addOrder(Order order, int id, List<Integer> services);

    Order findById(int id);

    void deleteOrder(int id);

    Order editOrder(int id, Order order,List<Integer> services,CurrentUser currentUser);

}