package com.example.hairsalonrest.service;

import com.example.hairsalonrest.repository.OrderRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.Order;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
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

        when(orderRepository.save(Mockito.any())).thenReturn(order);
        List<Order> all = orderService.findAll(currentUser);
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void addOrderTest() {
        int id = 75;
        Set<Integer> services = new HashSet<>();
        services.add(1);
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

        when(orderRepository.save(Mockito.any())).thenReturn(order);
        Order addOrder = orderService.addOrder(order, id, services);

        assertThat(addOrder.getId() == order.getId());
        //assertEquals(1, orderRepository.findAll().size());
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

        when(orderRepository.save(Mockito.any())).thenReturn(order);
        Order save = orderRepository.save(order);
        Order foundOrder = orderService.findById(order.getId());
        assertEquals(foundOrder.getId(), save.getId());
    }

    @Test
    public void editOrder() {
        int id = 6;
        Set<Integer> services = new HashSet<>();
        services.add(1);
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        CurrentUser currentUser = new CurrentUser(user);
        Order order = Order.builder()
                .id(id)
                .isDeleted(false)
                .user(user)
                .build();

        when(orderRepository.save(Mockito.any())).thenReturn(order);
        Order save = orderRepository.save(order);
        save.setIsDeleted(true);
        Order editOrder = orderService.editOrder(save.getId(), save, services, currentUser);
        assertEquals(editOrder.getIsDeleted(), is(true));
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
                .build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        orderService.deleteOrder(id);
        verify(orderRepository).deleteById(id);
    }


}
