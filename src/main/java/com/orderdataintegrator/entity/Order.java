package com.orderdataintegrator.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public record Order(Long orderId, String customerName, LocalDateTime orderDate, OrderStatus orderStatus) {
    public Order {
        Objects.requireNonNull(orderId, "orderId cannot be null");
//        Objects.requireNonNull(customerName, "customerName cannot be null");
//        Objects.requireNonNull(orderDate, "orderDate cannot be null");
//        Objects.requireNonNull(orderStatus, "orderStatus cannot be null");
    }
}
