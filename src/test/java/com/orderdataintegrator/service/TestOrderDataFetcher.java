package com.orderdataintegrator.service;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.OrderDataFetcher;

import java.util.List;
import java.util.Optional;

public class TestOrderDataFetcher implements OrderDataFetcher<Void> {
    @Override
    public List<Order> fetchOrderData(Optional<Void> param) {
        return List.of(
                new Order(1L, "John Doe", null, null),
                new Order(2L, "Jane Doe", null, null),
                new Order(3L, "Alice", null, null),
                new Order(4L, "Bob", null, null),
                new Order(5L, "Charlie", null, null),
                new Order(6L, "David", null, null),
                new Order(7L, "Eve", null, null)
        );
    }
}
