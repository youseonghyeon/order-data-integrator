package com.orderdataintegrator.external.converter;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExternalFetchConverter {

    public static Order toOrder(ExternalFetchResponse externalFetchResponse) {
        if (externalFetchResponse.getOrderId() == null
                || externalFetchResponse.getCustomerName() == null
                || externalFetchResponse.getOrderDate() == null) {
//            throw new IllegalArgumentException("Invalid order data received: Missing required fields");
            log.warn("Invalid order data received: Missing required fields. data: {}", externalFetchResponse);
            return null;
        }
        return new Order(
                externalFetchResponse.getOrderId(),
                externalFetchResponse.getCustomerName(),
                externalFetchResponse.getOrderDate(),
                OrderStatus.valueOf(externalFetchResponse.getOrderStatus())
        );
    }
}
