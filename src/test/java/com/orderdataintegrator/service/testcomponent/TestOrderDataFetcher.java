package com.orderdataintegrator.service.testcomponent;

import com.orderdataintegrator.external.OrderDataFetcher;
import com.orderdataintegrator.external.dto.ExternalFetchRequestQuery;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestOrderDataFetcher implements OrderDataFetcher {

    @Override
    public <T> List<T> fetchOrderData(ExternalFetchRequestQuery param, Function<ExternalFetchResponse, T> converter) {
        ExternalFetchResponse response1 = new ExternalFetchResponse(1L, "홍길동", LocalDateTime.now(), "PROCESSING", "Etc1");
        ExternalFetchResponse response2 = new ExternalFetchResponse(2L, "신짱구", LocalDateTime.now(), "SHIPPING", "Etc2");

        return Stream.of(response1, response2)
                .map(converter)
                .collect(Collectors.toList());
    }
}
