package com.orderdataintegrator.external;

import com.orderdataintegrator.external.dto.ExternalFetchRequestQuery;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class HttpOrderDataFetcher implements OrderDataFetcher {
    private final RestTemplate restTemplate;
    private final String externalServiceUrl;
    private static final HttpHeaders HEADERS;

    static {
        HEADERS = new HttpHeaders();
        HEADERS.set("Accept", "application/json");
    }

    @Override
    public <T> List<T> fetchOrderData(ExternalFetchRequestQuery input, Function<ExternalFetchResponse, T> converter) {
        HttpEntity<ExternalFetchRequestQuery> requestEntity = new HttpEntity<>(input, HEADERS);

        try {
            ResponseEntity<ExternalFetchResponse[]> response = restTemplate.exchange(
                    externalServiceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    ExternalFetchResponse[].class
            );

            return processResponse(response, converter);

        } catch (RestClientException e) {
            log.error("Network error while fetching order data: {}", e.getMessage(), e);
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Unexpected error while fetching order data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch order data", e);
        }
    }

    private <T> List<T> processResponse(ResponseEntity<ExternalFetchResponse[]> response, Function<ExternalFetchResponse, T> converter) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("Successfully fetched order data from external service.");
            log.info("Fetched order data: {}", Arrays.toString(response.getBody()));

            return Arrays.stream(response.getBody())
                    .map(converter)
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            log.warn("Received non-successful HTTP status code: {}", response.getStatusCode());
            return Collections.emptyList();
        }
    }
}
