package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;

import java.util.List;

public interface OrderRepository {

    void save(Order order);

    void saveAll(List<Order> orders);

    Order findById(Long id);

    List<Order> findAll();
}
