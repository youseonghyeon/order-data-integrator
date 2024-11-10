package com.orderdataintegrator.external.converter;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.dto.ExternalSenderRequest;

import java.util.List;

public class ExternalSendRequestConverter {

    public static ExternalSenderRequest convertToExternalSendRequest(List<Order> orders) {
        List<Order> filteredData = orders.stream().filter(ExternalSendRequestConverter::orderValidator).toList();
        return new ExternalSenderRequest("12345", filteredData);
    }

    // 필요 시 추가적인 validation 로직 구현
    private static boolean orderValidator(Order order) {
        return true;
    }

}
