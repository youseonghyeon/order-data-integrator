package com.orderdataintegrator.controller;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import com.orderdataintegrator.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderSearchingController.class)
class OrderSearchingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private final String URL_PREFIX = "/api/v1";

    @Test
    @DisplayName("주문 조회, 단건 조회 성공 테스트")
    void orderSearching() throws Exception {
        //given
        Long orderId = 1L;
        Order mockOrder = new Order(orderId, "홍길동", LocalDateTime.now(), OrderStatus.PROCESSING);
        given(orderService.findOrderById(orderId)).willReturn(mockOrder);
        //then
        mockMvc.perform(get(URL_PREFIX + "/search/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderId").value(1L))
                .andExpect(jsonPath("$.data.customerName").value("홍길동"))
                .andExpect(jsonPath("$.data.orderStatus").value("PROCESSING"));
    }

    @Test
    @DisplayName("주문 조회, 다건 조회 성공 테스트")
    void testOrderSearching() throws Exception {
        //given
        Order mockOrder1 = new Order(1L, "홍길동", LocalDateTime.now(), OrderStatus.PROCESSING);
        Order mockOrder2 = new Order(2L, "김철수", LocalDateTime.now(), OrderStatus.COMPLETED);
        given(orderService.findAllOrders()).willReturn(List.of(mockOrder1, mockOrder2));
        //then
        mockMvc.perform(get(URL_PREFIX + "/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].orderId").value(1L))
                .andExpect(jsonPath("$.data[0].customerName").value("홍길동"))
                .andExpect(jsonPath("$.data[0].orderStatus").value("PROCESSING"))
                .andExpect(jsonPath("$.data[1].orderId").value(2L))
                .andExpect(jsonPath("$.data[1].customerName").value("김철수"))
                .andExpect(jsonPath("$.data[1].orderStatus").value("COMPLETED"));
    }


}
