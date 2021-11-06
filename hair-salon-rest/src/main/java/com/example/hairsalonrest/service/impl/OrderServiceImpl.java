package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.OrderRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.OrderService;
import com.example.hairsalonrest.service.ServiceService;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Order;
import com.hairsaloncommon.model.Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final WorkerService workerService;
    private final ServiceService serviceService;

    @Override
    public List<Order> findAll(CurrentUser currentUser) {
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.getAllByUser_Id(currentUser.getUser().getId())) {
            if (!order.getIsDeleted()) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public Order addOrder(Order order, int id, Set<Integer> servicesId) {
        List<Service> services = new ArrayList<>();

        for (Integer serviceId : servicesId) {
            if ((serviceService.findById(serviceId).isPresent())) {
                services.add(serviceService.findById(serviceId).get());
            }
        }
        int duration = 0;
        for (Service service : services) {
            duration += service.getDuration();
        }

        order.setEndDatetime(order.getStartDatetime().plusMinutes(duration));
        List<Order> allByWorker = orderRepository.findAllByWorker(workerService.findWorkerById(id));
        for (Order order1 : allByWorker) {
            if (order1.getIsDeleted() ||
                    order.getStartDatetime().isAfter(order1.getStartDatetime()) &&
                    order.getStartDatetime().isBefore(order1.getEndDatetime()) ||
                    order.getEndDatetime().isBefore(order1.getEndDatetime()) &&
                    order.getEndDatetime().isAfter(order1.getStartDatetime())
            ) {
                return null;
            }
        }
        return orderRepository.save(order);
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
            orderRepository.save(byId);
        }
    }

    @Override
    public Order editOrder(int id, Order order, Set<Integer> servicesId, CurrentUser currentUser) {
        order.setUser(currentUser.getUser());
        Order byId = findById(id);
        order.setId(id);
        boolean isBusy = false;
        order.setIsDeleted(false);
        List<Service> services = new ArrayList<>();
        for (Integer serviceId : servicesId) {
            if (serviceService.findById(serviceId).isPresent()) {
                services.add(serviceService.findById(serviceId).get());
            }
        }
        for (Service service : services) {
            order.setEndDatetime(order.getStartDatetime().plusMinutes(service.getDuration()));
        }
        for (Order order1 : orderRepository.findAllByWorker(workerService.findWorkerById(id))) {
            if (order.getStartDatetime().isAfter(order1.getStartDatetime()) &&
                    order.getEndDatetime().isBefore(order1.getEndDatetime())) {
                isBusy = true;
            }
        }
        if (!isBusy) {
            mapper.map(order, byId);
            return orderRepository.save(order);
        }
        return order;
    }


}
