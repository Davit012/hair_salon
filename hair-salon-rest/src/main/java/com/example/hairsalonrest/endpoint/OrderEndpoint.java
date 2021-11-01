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

import java.util.ArrayList;
import java.util.List;

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
        for (int i = 0; i < orderCreateDto.getServices().size(); i++) {
            for (int j = i+1; j < orderCreateDto.getServices().size(); j++) {
                if (orderCreateDto.getServices().get(i) == orderCreateDto.getServices().get(j)){
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        if (workerById == null) {
            return ResponseEntity.notFound().build();
        }
        Order byId = orderService.addOrder(mapper.map(orderCreateDto, Order.class), id);
        workerById.getOrder().add(byId);
        orderCreateDto.setUser(currentUser.getUser());
        if (byId.getId() != 0) {
            workerService.save(workerById);
            return ResponseEntity.ok(mapper.map(byId, OrderDto.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") int id) {
        Order byId = orderService.findById(id);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.map(byId, OrderDto.class));
    }

    @PutMapping()
    public ResponseEntity<OrderDto> editOrder(@RequestBody OrderPutDto order) {
        int id = order.getWorker().getId();
        if (orderService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Order orderFromBd = orderService.editOrder(id, mapper.map(order, Order.class));
        return ResponseEntity.ok(mapper.map(orderFromBd, OrderDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id") int id) {
        if (orderService.findById(id) == null) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
