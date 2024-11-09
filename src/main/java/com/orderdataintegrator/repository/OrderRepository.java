package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void save(Order order);

    void saveAll(List<Order> orders);

    void update(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    void truncate();
}
