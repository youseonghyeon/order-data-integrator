package com.orderdataintegrator.service;

import com.orderdataintegrator.external.OrderDataFetcher;
import com.orderdataintegrator.external.dto.ExternalFetchRequestQuery;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;

import java.util.List;
import java.util.function.Function;

public class TestOrderDataFetcher implements OrderDataFetcher {

    @Override
    public <T> List<T> fetchOrderData(ExternalFetchRequestQuery param, Function<ExternalFetchResponse, T> converter) {
        return List.of();
    }
}
