package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDataFetcher<I> {

    List<Order> fetchOrderData(Optional<I> param);

}
