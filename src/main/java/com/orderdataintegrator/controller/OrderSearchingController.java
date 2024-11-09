package com.orderdataintegrator.controller;

import com.orderdataintegrator.dto.ApiResponse;
import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderSearchingController {

    private final OrderService orderService;

    @GetMapping("/search/{orderId}")
    public ResponseEntity<ApiResponse<Order>> orderSearching(@PathVariable Long orderId) {
        log.info("Searching order id {}", orderId);
        Order findOrder = orderService.findOrderById(orderId);
        log.info("Searching order id {} result: {}", orderId, findOrder);
        return ResponseEntity.ok(new ApiResponse<>("조회에 성공하였습니다.", findOrder));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Order>>> orderSearching() {
        log.info("Searching order list search");
        List<Order> allOrders = orderService.findAllOrders();
        return ResponseEntity.ok(new ApiResponse<>("조회에 성공하였습니다.", allOrders));
    }

}
