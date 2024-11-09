package com.orderdataintegrator.entity;

import java.time.LocalDateTime;

public record Order(Long orderId, String customerName, LocalDateTime orderDate, OrderStatus orderStatus) {}
