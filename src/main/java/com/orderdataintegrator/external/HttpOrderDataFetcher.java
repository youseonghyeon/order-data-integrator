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

    @Override
    public <T> List<T> fetchOrderData(ExternalFetchRequestQuery input, Function<ExternalFetchResponse, T> converter) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<ExternalFetchRequestQuery> requestEntity = new HttpEntity<>(input, headers);

        try {
            ResponseEntity<ExternalFetchResponse[]> response = restTemplate.exchange(
                    externalServiceUrl,
                    HttpMethod.GET,
                    requestEntity,
                    ExternalFetchResponse[].class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Successfully fetched order data from external service.");
                ExternalFetchResponse[] body = response.getBody();

                log.info("Fetched order data : {}", Arrays.toString(body));

                return Arrays.stream(body)
                        .map(converter)
                        .filter(Objects::nonNull)
                        .toList();
            } else {
                log.warn("Received non-successful HTTP status code: {}", response.getStatusCode());
                return Collections.emptyList();
            }

        } catch (RestClientException e) {
            log.error("Network error while fetching order data: {}", e.getMessage(), e);
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("An unexpected error occurred while processing order data: {}", e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred while fetching order data", e);
        }
    }
}
