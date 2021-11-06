package com.example.hairsalonrest.endpoint;


import com.example.hairsalonrest.dto.orderdtos.OrderCreateDto;
import com.example.hairsalonrest.dto.orderdtos.OrderDto;
import com.example.hairsalonrest.dto.orderdtos.OrderPutDto;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.OrderService;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Order;
import com.hairsaloncommon.model.Worker;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderEndpoint {

    private final OrderService orderService;
    private final ModelMapper mapper;
    private final WorkerService workerService;


    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(@AuthenticationPrincipal CurrentUser currentUser) {
        List<Order> all = orderService.findAll(currentUser);
        List<OrderDto> orderDtos = new ArrayList<>();
        if (all.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        for (Order order : all) {
            OrderDto orderDto = mapper.map(order, OrderDto.class);
            orderDtos.add(orderDto);
        }
        return ResponseEntity.ok(orderDtos);
    }

    @PostMapping()
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderCreateDto orderCreateDto, @AuthenticationPrincipal CurrentUser currentUser) {
        int id = orderCreateDto.getWorker().getId();
        Worker workerById = workerService.findWorkerById(id);
        Set<Integer> services = new HashSet<>();
        for (int i = 0; i < orderCreateDto.getWorker().getServices().size(); i++) {
            services.add(orderCreateDto.getWorker().getServices().get(i).getId());
        }
        orderCreateDto.setUser(currentUser.getUser());
        if (workerById == null) {
            return ResponseEntity.notFound().build();
        }
        Order orderFromService = orderService.addOrder(mapper.map(orderCreateDto, Order.class),
                id, services);
        if (orderFromService == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mapper.map(orderFromService, OrderDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") int id) {
        Order byId = orderService.findById(id);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.map(byId, OrderDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> editOrder(@PathVariable("id") int id, @RequestBody OrderPutDto order, @AuthenticationPrincipal CurrentUser currentUser) {
        Set<Integer> services = new HashSet<>();
        for (int i = 0; i < order.getWorker().getServices().size(); i++) {
            services.add(order.getWorker().getServices().get(i).getId());
        }
        int workerId = order.getWorker().getId();
        if (orderService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Order orderFromBd = orderService.editOrder(workerId, mapper.map(order, Order.class), services, currentUser);
        return ResponseEntity.ok(mapper.map(orderFromBd, OrderDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id") int id) {
        if (orderService.findById(id) != null) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
