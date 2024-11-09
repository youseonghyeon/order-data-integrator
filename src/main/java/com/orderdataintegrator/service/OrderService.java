package com.orderdataintegrator.service;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.OrderDataFetcher;
import com.orderdataintegrator.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDataFetcher<Void> orderDataFetcher;
    private final OrderRepository orderRepository;

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("order id not found error"));
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public void fetchAndSaveOrders() {
        List<Order> orders = orderDataFetcher.fetchOrderData(null);
        if (orders.isEmpty()) {
            log.warn("No order data fetched.");
            return;
        }
        saveOrUpdateOrders(orders);
    }

    private void saveOrUpdateOrders(List<Order> orders) {
        orders.forEach(this::saveOrUpdateOrder);
    }

    private void saveOrUpdateOrder(Order order) {
        if (!orderRepository.existsById(order.orderId())) {
            log.info("Saving a new order: {}", order);
            orderRepository.save(order);
        } else {
            log.info("Updating an existing order: {}", order);
            orderRepository.update(order);
        }
    }
}
