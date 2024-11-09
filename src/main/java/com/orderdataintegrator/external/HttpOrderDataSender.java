package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;
import lombok.Getter;
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

    @Override
    public boolean sendOrderData(List<Order> orderList) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<List<Order>> requestEntity = new HttpEntity<>(orderList, headers);

        try {
            ResponseEntity<ExternalSenderResponse> response = restTemplate.exchange(
                    orderExternalServiceUrl, HttpMethod.POST, requestEntity, ExternalSenderResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Order data sent successfully: {}", response.getBody().getMessage());
                return true;
            } else {
                log.error("Failed to send order data. Status code: {}", response.getStatusCode());
                log.error("Response body: {}", response.getBody());
                return false;
            }

        } catch (RestClientException e) {
            log.error("Failed to send order data due to network error: {}", e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("An unexpected error occurred while sending order data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send order data due to an unexpected error", e);
        }
    }

    @Getter
    static class ExternalSenderResponse {
        private String message;
        private String etc;
    }
}
