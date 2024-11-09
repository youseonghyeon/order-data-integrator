package com.orderdataintegrator.external;

import java.util.List;

public interface OrderDataFetcher<I, O> {

    List<O> fetchOrderData(I input);

}
