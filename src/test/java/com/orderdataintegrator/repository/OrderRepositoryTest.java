package com.orderdataintegrator.repository;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import com.orderdataintegrator.exception.DuplicateOrderException;
import com.orderdataintegrator.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void tearDown() {
        orderRepository.truncate();
    }

    @Test
    @DisplayName("주문 단건 저장 성공")
    void saveSingleOrder() {
        Order mockOrder = createMockOrder(1L);
        orderRepository.save(mockOrder);
        Optional<Order> savedOrder = orderRepository.findById(1L);

        assertTrue(savedOrder.isPresent(), "주문 단건 저장 실패");
        assertEquals(mockOrder, savedOrder.get(), "저장된 주문이 일치하지 않습니다.");
    }

    @Test
    @DisplayName("주문 다건 저장 성공")
    void saveMultipleOrders() {
        List<Order> orders = List.of(createMockOrder(1L), createMockOrder(2L));
        orderRepository.saveAll(orders);

        assertAll("다중 주문 저장 및 조회 검증",
                () -> assertEquals(orders.get(0), orderRepository.findById(1L).orElse(null)),
                () -> assertEquals(orders.get(1), orderRepository.findById(2L).orElse(null))
        );
    }

    @Test
    @DisplayName("주문 단건 저장 실패 - 중복 주문 ID로 인한 롤백 처리")
    void saveSingleOrderWithDuplicateId() {
        Order mockOrder = createMockOrder(1L);
        orderRepository.save(mockOrder);

        assertThrows(DuplicateOrderException.class, () -> orderRepository.save(mockOrder), "중복 주문 ID로 인한 저장 실패 검증 실패");
    }

    @Test
    @DisplayName("주문 다건 저장 실패 - 중복 주문 ID로 인한 롤백 처리")
    void saveMultipleOrdersWithDuplicateId() {
        Order firstOrder = createMockOrder(1L);
        Order secondOrder = createMockOrder(2L);
        Order duplicateOrder = createMockOrder(1L);

        orderRepository.saveAll(List.of(firstOrder, secondOrder));
        assertThrows(DuplicateOrderException.class, () -> orderRepository.saveAll(List.of(duplicateOrder)), "중복 주문 ID로 인한 저장 실패 검증 실패");
    }

    @Test
    @DisplayName("주문 단건 수정 성공")
    void updateOrder() {
        Order mockOrder = createMockOrder(1L);
        orderRepository.save(mockOrder);

        Order updatedOrder = new Order(1L, "홍길동", LocalDateTime.of(2024, 11, 9, 13, 30), OrderStatus.PROCESSING);
        orderRepository.update(updatedOrder);

        Optional<Order> foundOrder = orderRepository.findById(1L);
        assertTrue(foundOrder.isPresent(), "주문 단건 수정 실패");
        assertEquals(updatedOrder, foundOrder.get(), "수정된 주문 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("주문 단건 수정 실패 - 존재하지 않는 주문 ID")
    void updateOrderNotFound() {
        Order mockOrder = createMockOrder(1L);
        orderRepository.save(mockOrder);

        Order updatedOrder = new Order(90L, "홍길동", LocalDateTime.of(2024, 11, 9, 13, 30), OrderStatus.PROCESSING);
        assertThrows(OrderNotFoundException.class, () -> orderRepository.update(updatedOrder), "존재하지 않는 주문 수정 검증 실패");
    }

    @Test
    @DisplayName("주문 단건 조회 성공")
    void findOrderById() {
        Order mockOrder = createMockOrder(1L);
        orderRepository.save(mockOrder);

        Optional<Order> foundOrder = orderRepository.findById(1L);
        assertTrue(foundOrder.isPresent(), "주문 단건 조회 실패");
        assertEquals(mockOrder, foundOrder.get(), "조회된 주문 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("주문 단건 조회 실패 - 존재하지 않는 주문 ID")
    void findOrderByIdNotFound() {
        Optional<Order> foundOrder = orderRepository.findById(90L);
        assertTrue(foundOrder.isEmpty(), "존재하지 않는 주문 조회 검증 실패");
    }

    @Test
    @DisplayName("주문 다건 조회 성공")
    void findAllOrders() {
        insertMockOrders(1, 1000);
        List<Order> allOrders = orderRepository.findAll();

        assertEquals(1000, allOrders.size(), "주문 다건 조회 실패");
    }

    @Test
    @DisplayName("주문 존재 여부 확인 성공")
    void existsById() {
        Order mockOrder = createMockOrder(1L);
        orderRepository.save(mockOrder);

        assertTrue(orderRepository.existsById(1L), "주문 존재 여부 확인 실패");
    }

    @Test
    @DisplayName("주문 존재 여부 확인 실패 - 존재하지 않는 주문 ID")
    void existsByIdNotFound() {
        assertFalse(orderRepository.existsById(90L), "존재하지 않는 주문 존재 여부 확인 실패");
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
