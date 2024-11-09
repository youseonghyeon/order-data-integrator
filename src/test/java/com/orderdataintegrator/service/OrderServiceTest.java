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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @TestConfiguration
    static class OrderServiceTestConfig {
        @Bean
        public OrderService orderService(OrderRepository orderRepository) {
            return new OrderService(new TestOrderDataFetcher(), orderRepository);
        }
    }

    @BeforeEach
    void tearDown() {
        orderRepository.truncate();
    }

    @Test
    @DisplayName("주문 단건 조회 성공")
    void findOrderById() {
        // Given
        Order order = createMockOrder(3528L);
        orderRepository.save(order);
        // When
        Order foundOrder = orderService.findOrderById(3528L);
        // Then
        assertEquals(order, foundOrder);
    }

    @Test
    @DisplayName("주문 단건 조회 실패 - 주문 ID가 존재하지 않음")
    void findOrderByIdFail() {
        Assertions.assertThrows(NoSuchElementException.class, () -> orderService.findOrderById(1L));
    }

    @Test
    @DisplayName("주문 리스트 조회 성공")
    void findAllOrders() {
        //given
        insertMockOrders(1, 1000);
        //when
        List<Order> allOrders = orderService.findAllOrders();
        //then
        assertEquals(1000, allOrders.size());
    }

    @Test
    @DisplayName("주문 리스트 조회 실패 - 주문 데이터가 존재하지 않음")
    void findAllOrdersFail() {
        Assertions.assertTrue(orderService.findAllOrders().isEmpty());
    }

    @Test
    @DisplayName("주문 데이터 조회 및 저장 (테스트 Config TestOrderDataFetcher 사용)")
    void fetchAndSaveOrders() {
        // When
        orderService.fetchAndSaveOrders();
        // Then
        assertFalse(orderService.findAllOrders().isEmpty());
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
