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

    @Override
    public boolean sendOrderData(List<Order> orderList) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ExternalSenderRequest externalSenderRequest = ExternalSendRequestConverter.convertToExternalSendRequest(orderList);

        HttpEntity<ExternalSenderRequest> requestEntity = new HttpEntity<>(externalSenderRequest, headers);

        try {
            ResponseEntity<ExternalSenderResponse> response = restTemplate.exchange(
                    orderExternalServiceUrl, HttpMethod.POST, requestEntity, ExternalSenderResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Successfully send order data to external service. {}", response.getBody().getMessage());
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


}
