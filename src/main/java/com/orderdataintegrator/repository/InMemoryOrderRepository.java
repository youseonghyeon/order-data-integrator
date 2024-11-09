package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;

import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orderStore = new HashMap<>();

    @Override
    public void save(Order order) {
        if (orderStore.containsKey(order.orderId())) {
            throw new IllegalStateException("order id duplicated error");
        }
        orderStore.put(order.orderId(), order);
    }

    @Override
    public void saveAll(List<Order> orders) {
        boolean existingOrder = orders.stream().anyMatch(order -> orderStore.containsKey(order.orderId()));
        if (existingOrder) {
            throw new IllegalStateException("order id duplicated error");
        }
        for (Order order : orders) {
            orderStore.put(order.orderId(), order);
        }
    }

    @Override
    public void update(Order order) {
        if (!orderStore.containsKey(order.orderId())) {
            throw new NoSuchElementException("order id not found error");
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
