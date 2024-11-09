package com.orderdataintegrator.service;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import com.orderdataintegrator.exception.ApiException;
import com.orderdataintegrator.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void tearDown() {
        orderRepository.truncate();
    }

    @Test
    @DisplayName("주문 단건 조회 성공")
    void findOrderById() {
        //given
        LocalDateTime orderDate = LocalDateTime.of(2024, 11, 9, 13, 30);
        Order order = new Order(3528L, "홍길동", orderDate, OrderStatus.PROCESSING);
        orderRepository.save(order);
        //when
        Order findOrder = orderService.findOrderById(3528L);
        //then
        Assertions.assertEquals(order, findOrder);
    }

    @Test
    @DisplayName("주문 단건 조회 실패 - 주문 ID가 존재하지 않음")
    void findOrderByIdFail() {
        //then
        Assertions.assertThrows(ApiException.class, () -> orderService.findOrderById(1L));
    }

    @Test
    @DisplayName("주문 리스트 조회 성공")
    void findAllOrders() {
        //given
        insertMockOrders(1, 1000);
        //when
        List<Order> allOrders = orderService.findAllOrders();
        //then
        Assertions.assertEquals(1000, allOrders.size());
    }

    @Test
    @DisplayName("주문 리스트 조회 실패 - 주문 데이터가 존재하지 않음")
    void findAllOrdersFail() {
        //then
        Assertions.assertTrue(orderService.findAllOrders().isEmpty());
    }

    @Test
    void fetchAndSaveOrders() {
        //given
        //when
        orderService.fetchAndSaveOrders();
        //then
        List<Order> allOrders = orderService.findAllOrders();
        Assertions.assertFalse(allOrders.isEmpty());
    }

    private Order createMockOrder(Long orderId) {
        LocalDateTime orderDate = LocalDateTime.of(2024, 11, 9, 13, 30);
        return new Order(orderId, "홍길동", orderDate, OrderStatus.PROCESSING);
    }

    private void insertMockOrders(long startId, long count) {
        for (long id = startId; id < startId + count; id++) {
            orderRepository.save(createMockOrder(id));
        }
    }
}
