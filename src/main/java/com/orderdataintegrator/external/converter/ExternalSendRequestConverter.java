package com.orderdataintegrator.external.converter;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.dto.ExternalSenderRequest;

import java.util.List;

public class ExternalSendRequestConverter {

    /**
     * 주어진 주문 목록을 유효성 검사 후 ExternalSenderRequest로 변환합니다.
     *
     * @param orders 변환할 주문 목록
     * @return 변환된 ExternalSenderRequest
     */
    public static ExternalSenderRequest convertToExternalSendRequest(List<Order> orders) {
        List<Order> filteredData = orders.stream()
                .filter(ExternalSendRequestConverter::orderValidator)
                .toList();

        String dummyRequestId = "dummyRequestId";
        return new ExternalSenderRequest(dummyRequestId, filteredData);
    }

    /**
     * 주문 유효성 검사 로직을 구현하여 유효한 주문만 필터링합니다.
     *
     * @param order 검사할 주문
     * @return 유효한 주문일 경우 true, 그렇지 않으면 false
     */
    private static boolean orderValidator(Order order) {
        return order != null &&
                order.orderId() != null &&
                order.customerName() != null &&
                order.orderDate() != null &&
                order.orderStatus() != null;
    }

}
