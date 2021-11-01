package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.OrderRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.OrderService;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Order;
import com.hairsaloncommon.model.Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final WorkerService workerService;

    @Override
    public List<Order> findAll(CurrentUser currentUser) {
        return orderRepository.getAllByUser_Id(currentUser.getUser().getId());
    }

    @Override
    public Order addOrder(Order order, int id) {
        boolean isBusy = false;
        order.setIsDeleted(false);
        for (Service service : order.getServices()) {
            order.setEndDatetime(order.getStartDatetime().plusMinutes(service.getDuration()));
        }
        for (Order order1 : orderRepository.findAllByWorker_Id(id)) {
            if (order.getStartDatetime().isAfter(order1.getStartDatetime()) &&
                    order.getEndDatetime().isBefore(order1.getEndDatetime())) {
                isBusy = true;
            }
        }
        if (!isBusy) {
            return orderRepository.save(order);
        }
        return order;
    }

    @Override
    public Order findById(int id) {
        return orderRepository.getById(id);
    }

    @Override
    public void deleteOrder(int id) {
        Order byId = orderRepository.getById(id);
        if (byId.getStartDatetime().minusHours(24).isAfter(LocalDateTime.now())) {
            byId.setIsDeleted(true);
        }
    }

    @Override
    public Order editOrder(int id, Order order) {
        boolean isBusy = false;
        Order byId = orderRepository.getById(id);
        byId.setId(id);
        if (byId.getStartDatetime().minusHours(24).isAfter(LocalDateTime.now())) {
            for (Service service : order.getServices()) {
                order.setEndDatetime(order.getStartDatetime().plusMinutes(service.getDuration()));
            }

            for (Order order1 : orderRepository.findAllByWorker_Id(id)) {
                if (order.getStartDatetime().isAfter(order1.getStartDatetime()) &&
                        order.getEndDatetime().isBefore(order1.getEndDatetime())) {
                    isBusy = true;
                }
                if (!isBusy) {
                    mapper.map(order, byId);
                    return orderRepository.save(order);
                }

            }
        }
        return null;
    }


}
