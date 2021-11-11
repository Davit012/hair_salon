package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.example.hairsalonrest.repository.OrderRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private WorkerService workerService;
    @MockBean
    private ServiceService serviceService;

    @Autowired
    private OrderService orderService;

    @Test
    public void findAll() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        CurrentUser currentUser = new CurrentUser(user);

        Order order = Order.builder()
                .id(1)
                .isDeleted(false)
                .user(user)
                .build();

        when(orderRepository.getAllByUser_Id(currentUser.getUser().getId())).thenReturn(Arrays.asList(order));
        List<Order> all = orderService.findAll(currentUser);
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void addOrderTest() {
        int id = 75;

        Service service = Service.builder()
                .id(1)
                .build();

        Set<Integer> services = new HashSet<>();
        services.add(service.getId());

        Worker worker = Worker.builder()
                .id(1)
                .build();

        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();
        Order order = Order.builder()
                .id(1)
                .isDeleted(false)
                .user(user)
                .endDatetime(LocalDateTime.now())
                .startDatetime(LocalDateTime.now())
                .build();

        when(workerService.findWorkerById(worker.getId())).thenReturn(worker);
        when(orderRepository.findAllByWorker(worker)).thenReturn(Arrays.asList(order));
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(serviceService.findById(service.getId())).thenReturn(Optional.of(service));
        when(orderRepository.save(order)).thenReturn(order);

        Order addOrder = orderService.addOrder(order, id, services);
        assertEquals(addOrder.getId(), order.getId());
        assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    public void findById() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        Order order = Order.builder()
                .id(1)
                .isDeleted(false)
                .user(user)
                .build();

        when(orderRepository.getById(order.getId())).thenReturn(order);
        Order foundOrder = orderService.findById(order.getId());
        assertEquals(foundOrder.getId(), order.getId());
    }

    @Test
    public void editOrder() {
        int id = 6;
        Service service = Service.builder()
                .id(1)
                .build();

        Set<Integer> services = new HashSet<>();
        services.add(service.getId());

        Worker worker = Worker.builder()
                .id(1)
                .name("Ali")
                .build();

        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        CurrentUser currentUser = new CurrentUser(user);

        Order order = Order.builder()
                .id(1)
                .isDeleted(false)
                .user(user)
                .endDatetime(LocalDateTime.now())
                .startDatetime(LocalDateTime.now())
                .build();

        when(workerService.findWorkerById(worker.getId())).thenReturn(worker);
        when(orderRepository.findAllByWorker(worker)).thenReturn(Arrays.asList(order));
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(serviceService.findById(service.getId())).thenReturn(Optional.of(service));
        when(orderRepository.getById(any())).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        Order save = orderRepository.save(order);
        save.setWorker(new Worker(2, "John", "Smith", "0000", null));
        Order editOrder = orderService.editOrder(order.getId(), save, services, currentUser);
        assertEquals(editOrder.getWorker().getName(), "John");
    }

    @Test
    public void deleteOrderTest() {
        int id = 6;
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        Order order = Order.builder()
                .id(id)
                .isDeleted(false)
                .user(user)
                .startDatetime(LocalDateTime.now().plusDays(1))
                .build();

        when(orderRepository.getById(id)).thenReturn(order);
        assertEquals(order.getIsDeleted(), Boolean.FALSE);
        order.setIsDeleted(true);
        when(orderRepository.save(order)).thenReturn(order);
        orderService.deleteOrder(id);
        assertEquals(order.getIsDeleted(), Boolean.TRUE);
    }


}
