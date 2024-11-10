package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.converter.ExternalSendRequestConverter;
import com.orderdataintegrator.external.dto.ExternalSenderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class HttpOrderDataSenderTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private HttpOrderDataSender dataSender;

    private final String orderExternalServiceUrl = "https://test.com/api/orders";

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        dataSender = new HttpOrderDataSender(restTemplate, orderExternalServiceUrl);
    }

    @Test
    @DisplayName("주문 데이터 전송 성공")
    void testSendOrderData() {
        // Arrange
        List<Order> orderList = new ArrayList<>(); // 실제 Order 객체로 대체할 수 있습니다.
        ExternalSenderRequest expectedRequest = ExternalSendRequestConverter.convertToExternalSendRequest(orderList);

        String body = "{\"message\": \"Order sent successfully\"}";

        mockServer.expect(once(), requestTo(orderExternalServiceUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        // Act
        boolean result = dataSender.sendOrderData(orderList);

        // Assert
        assertTrue(result);
        mockServer.verify();
    }

    @Test
    @DisplayName("주문 데이터 전송 실패 (네트워크 에러)")
    void testSendOrderDataNetworkError() {
        // Arrange
        List<Order> orderList = new ArrayList<>(); // 실제 Order 객체로 대체할 수 있습니다.

        mockServer.expect(once(), requestTo(orderExternalServiceUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        // Act
        boolean result = dataSender.sendOrderData(orderList);

        // Assert
        assertFalse(result);
        mockServer.verify();
    }

    @Test
    @DisplayName("주문 데이터 전송 실패 (2xx 외의 응답 코드)")
    void testSendOrderDataNon2xxResponse() {
        // Arrange
        List<Order> orderList = new ArrayList<>();

        mockServer.expect(once(), requestTo(orderExternalServiceUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\": \"Invalid request\"}"));

        // Act
        boolean result = dataSender.sendOrderData(orderList);

        // Assert
        assertFalse(result);
        mockServer.verify();
    }
}
