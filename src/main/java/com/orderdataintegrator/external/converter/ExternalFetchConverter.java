package com.orderdataintegrator.external.converter;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import com.orderdataintegrator.external.dto.ExternalFetchResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class ExternalFetchConverter {

    /**
     * 필수 필드가 누락되었거나 잘못된 상태값이 포함된 경우 null 을 반환합니다.
     * 변환 과정에서 예외가 발생하면 로그를 기록하고 안전하게 빈 결과를 반환하도록 합니다.
     *
     * @param externalFetchResponse 변환할 ExternalFetchResponse 객체
     * @return 변환된 Order 객체를 포함한 Optional, 변환 실패 시 Optional.empty()
     */
    public static Order toOrder(ExternalFetchResponse externalFetchResponse) {
        if (externalFetchResponse.getOrderId() == null
                || externalFetchResponse.getCustomerName() == null
                || externalFetchResponse.getOrderDate() == null) {
//            throw new IllegalArgumentException("Invalid order data received: Missing required fields");
            log.warn("Invalid order data received: Missing required fields. data: {}", externalFetchResponse);
            return null;
        }
        // OrderStatus 변환 중 잘못된 값이 있을 때 처리
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(externalFetchResponse.getOrderStatus());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid order status received: {}. data: {}", externalFetchResponse.getOrderStatus(), externalFetchResponse);
            return null;
        }
        return new Order(
                externalFetchResponse.getOrderId(),
                externalFetchResponse.getCustomerName(),
                externalFetchResponse.getOrderDate(),
                orderStatus
        );
    }
}
