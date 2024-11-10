package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.converter.ExternalSendRequestConverter;
import com.orderdataintegrator.external.dto.ExternalSenderRequest;
import com.orderdataintegrator.external.dto.ExternalSenderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class HttpOrderDataSender implements OrderDataSender {

    private final RestTemplate restTemplate;
    private final String orderExternalServiceUrl;
    private static final HttpHeaders HEADERS;

    static {
        HEADERS = new HttpHeaders();
        HEADERS.set("Content-Type", "application/json");
    }

    @Override
    public boolean sendOrderData(List<Order> orderList) {
        ExternalSenderRequest externalSenderRequest = ExternalSendRequestConverter.convertToExternalSendRequest(orderList);
        HttpEntity<ExternalSenderRequest> requestEntity = new HttpEntity<>(externalSenderRequest, HEADERS);

        try {
            ResponseEntity<ExternalSenderResponse> response = restTemplate.exchange(
                    orderExternalServiceUrl, HttpMethod.POST, requestEntity, ExternalSenderResponse.class
            );
            return processResponse(response);

        } catch (RestClientException e) {
            log.error("Network error while sending order data: {}", e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected error while sending order data: {}", e.getMessage(), e);
            throw new OrderDataSendingException("Failed to send order data due to an unexpected error", e);
        }
    }

    private boolean processResponse(ResponseEntity<ExternalSenderResponse> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("Order data sent successfully to external service: {}", response.getBody().getMessage());
            return true;
        } else {
            log.error("Failed to send order data. Status code: {}", response.getStatusCode());
            log.error("Response body: {}", response.getBody());
            return false;
        }
    }

    public static class OrderDataSendingException extends RuntimeException {
        public OrderDataSendingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
