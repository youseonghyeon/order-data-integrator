package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void save() {
        Order order = new Order();
        orderRepository.save(order);
    }

    @Test
    void saveAll() {
        Order order = new Order();
        orderRepository.saveAll(List.of(order));
    }

    @Test
    void findById() {
        orderRepository.findById(1L);
    }

    @Test
    void findAll() {
        List<Order> orders = orderRepository.findAll();
    }
}
