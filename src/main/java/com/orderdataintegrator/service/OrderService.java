package com.orderdataintegrator.service;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.OrderDataFetcher;
import com.orderdataintegrator.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDataFetcher<Void> orderDataFetcher;
    private final OrderRepository orderRepository;

    public void fetchAndSaveOrders() {
        List<Order> orders = orderDataFetcher.fetchOrderData(null);
        saveOrUpdateOrders(orders);
    }

    private void saveOrUpdateOrders(List<Order> orders) {
        for (Order order : orders) {
            if (!orderRepository.existsById(order.orderId())) {
                orderRepository.save(order);
            } else {
                orderRepository.update(order);
            }
        }
    }

}
