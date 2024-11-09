package com.orderdataintegrator.controller;

import com.orderdataintegrator.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderSearchingController {

    private final OrderService orderService;

    @GetMapping("/search/{orderId}")
    public String orderSearching(@PathVariable String orderId) {
        log.info("Searching order id {}", orderId);
        return "Searching order id " + orderId;
    }

    @GetMapping("/search")
    public String orderSearching() {
        log.info("Searching order search");
        return "Searching order search";
    }



}
