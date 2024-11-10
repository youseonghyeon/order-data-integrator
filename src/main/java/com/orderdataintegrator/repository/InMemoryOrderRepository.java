package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.exception.DuplicateOrderException;
import com.orderdataintegrator.exception.OrderNotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orderStore = new ConcurrentHashMap<>();

    @Override
    public void save(Order order) {
        if (orderStore.containsKey(order.orderId())) {
            throw new DuplicateOrderException("Order ID already exists: " + order.orderId());
        }
        orderStore.put(order.orderId(), order);
    }

    @Override
    public void saveAll(List<Order> orders) {
        for (Order order : orders) {
            save(order); // save 메서드를 호출하여 중복 검사와 추가를 일관되게 처리
        }
    }

    @Override
    public void update(Order order) {
        if (!orderStore.containsKey(order.orderId())) {
            throw new OrderNotFoundException("Order ID not found: " + order.orderId());
        }
        orderStore.put(order.orderId(), order);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(orderStore.get(orderId));
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(orderStore.values());
    }

    @Override
    public void truncate() {
        orderStore.clear();
    }

    @Override
    public boolean existsById(Long orderId) {
        return orderStore.containsKey(orderId);
    }
}
