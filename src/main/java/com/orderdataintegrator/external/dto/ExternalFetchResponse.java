package com.orderdataintegrator.external.dto;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExternalFetchResponse {

    private Long orderId;
    private String customerName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String etc;

    public static Order toOrder(ExternalFetchResponse externalFetchResponse) {
        // ## 값이 없을 때 처리해야 할 로직
        if (externalFetchResponse.orderId == null || externalFetchResponse.orderDate == null || externalFetchResponse.orderStatus == null) {
            // 1. 예외 발생 전략
//            throw new IllegalArgumentException("Invalid order data received: Missing required fields");
            // 2. 값이 없을 시 filtering 처리 전략
            return null;
        }
        return new Order(
                externalFetchResponse.orderId,
                externalFetchResponse.customerName,
                externalFetchResponse.orderDate,
                externalFetchResponse.orderStatus
        );
    }

}
