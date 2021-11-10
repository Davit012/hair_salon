package com.hairsaloncommon.service;

import com.hairsaloncommon.model.Order;
import com.hairsaloncommon.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {

    List<Order> findAll(User currentUser);

    Order addOrder(Order order, int id, Set<Integer> services);

    Order findById(int id);

    void deleteOrder(int id);

    Order editOrder(int id, Order order, Set<Integer> services, User currentUser);

}