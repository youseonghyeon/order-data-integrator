package com.orderdataintegrator.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import com.orderdataintegrator.external.dto.ExternalFetchRequestQuery;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class HttpOrderDataFetcherTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private HttpOrderDataFetcher dataFetcher;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final String externalServiceUrl = "https://example.com/api/orders";

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        objectMapper.registerModule(new JavaTimeModule());
        mockServer = MockRestServiceServer.createServer(restTemplate);
        dataFetcher = new HttpOrderDataFetcher(restTemplate, externalServiceUrl);
    }

    @Test
    @DisplayName("주문 데이터 호출 성공")
    void testFetchOrderDataSuccess() throws JsonProcessingException {
        // Arrange
        ExternalFetchRequestQuery query = new ExternalFetchRequestQuery("test-data");
        Function<ExternalFetchResponse, Order> converter = ExternalFetchResponse::toOrder;

        ExternalFetchResponse bodyObject = new ExternalFetchResponse(12L, "홍길동", LocalDateTime.now(), OrderStatus.SHIPPING.name(), "etc");
        String body = objectMapper.writeValueAsString(Collections.singletonList(bodyObject));
        mockServer.expect(once(), requestTo(externalServiceUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        // Act
        List<Order> result = dataFetcher.fetchOrderData(query, converter);

        // Assert
        assertEquals(1, result.size());
        assertEquals(bodyObject.getOrderId(), result.get(0).orderId());
        assertEquals(bodyObject.getCustomerName(), result.get(0).customerName());
        assertEquals(bodyObject.getOrderDate(), result.get(0).orderDate());
        assertEquals(OrderStatus.SHIPPING, result.get(0).orderStatus());

        mockServer.verify();
    }

    @Test
    @DisplayName("주문 데이터 호출 실패 - 2xx 외의 응답 코드")
    void testFetchOrderData_Non2xxResponse() {
        // Arrange
        ExternalFetchRequestQuery query = new ExternalFetchRequestQuery("test-data");
        Function<ExternalFetchResponse, String> converter = ExternalFetchResponse::toString;

        mockServer.expect(once(), requestTo(externalServiceUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        // Act
        List<String> result = dataFetcher.fetchOrderData(query, converter);

        // Assert
        assertTrue(result.isEmpty());
        mockServer.verify();
    }

    @Test
    @DisplayName("주문 데이터 호출 실패 - 네트워크 에러")
    void testFetchOrderDataNetworkError() {
        // Arrange
        ExternalFetchRequestQuery query = new ExternalFetchRequestQuery("test-data");
        Function<ExternalFetchResponse, String> converter = ExternalFetchResponse::toString;

        mockServer.expect(once(), requestTo(externalServiceUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        // Act
        List<String> result = dataFetcher.fetchOrderData(query, converter);

        // Assert
        assertTrue(result.isEmpty());
        mockServer.verify();
    }
}
