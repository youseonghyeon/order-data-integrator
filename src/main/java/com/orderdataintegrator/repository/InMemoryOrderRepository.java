package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> orderStore = new HashMap<>();

    @Override
    public void save(Order order) {
        // TODO Functionality needs to be implemented
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveAll(List<Order> orders) {
        // TODO Functionality needs to be implemented
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Order findById(Long id) {
        // TODO Functionality needs to be implemented
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Order> findAll() {
        // TODO Functionality needs to be implemented
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
