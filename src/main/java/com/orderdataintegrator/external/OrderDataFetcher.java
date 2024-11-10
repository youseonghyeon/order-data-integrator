package com.orderdataintegrator.external;

import com.orderdataintegrator.external.dto.ExternalFetchRequestQuery;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;

import java.util.List;
import java.util.function.Function;

public interface OrderDataFetcher {

    <T> List<T> fetchOrderData(ExternalFetchRequestQuery param, Function<ExternalFetchResponse, T> converter);

}
