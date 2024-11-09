package com.orderdataintegrator.controller;

import com.orderdataintegrator.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        String orderId = "1";
        mockMvc.perform(get(URL_PREFIX + "/search/" + orderId))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("주문 조회, 다건 조회 성공 테스트")
    void testOrderSearching() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/search"))
                .andExpect(status().isOk());
    }


}
