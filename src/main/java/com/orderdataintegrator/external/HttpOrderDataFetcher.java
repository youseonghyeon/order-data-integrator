package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class HttpOrderDataFetcher implements OrderDataFetcher<Void> {

    private final RestTemplate restTemplate;
    private final String externalServiceUrl;

    @Override
    public List<Order> fetchOrderData(Optional<Void> input) {
        try {
            ExternalResponse[] response = restTemplate.getForObject(externalServiceUrl, ExternalResponse[].class);

            if (response == null) {
                log.warn("Received null response from external service");
                return Collections.emptyList();
            }

            return Arrays.stream(response)
                    .map(ExternalResponse::toOrder)
                    .toList();

        } catch (RestClientException e) {
            log.error("Failed to fetch order data from external service: {}", e.getMessage(), e);
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("An error occurred while processing order data: {}", e.getMessage(), e);
            throw new RuntimeException("An error occurred while fetching order data", e);
        }
    }

    @Getter
    static class ExternalResponse {
        private Long orderId;
        private String customerName;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private String etc;

        protected static Order toOrder(ExternalResponse externalResponse) {
            if (externalResponse.orderId == null || externalResponse.customerName == null || externalResponse.orderDate == null) {
                throw new IllegalArgumentException("Invalid order data received: Missing required fields");
            }
            return new Order(
                    externalResponse.orderId,
                    externalResponse.customerName,
                    externalResponse.orderDate,
                    externalResponse.orderStatus
            );
        }
    }
}
