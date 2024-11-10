package com.orderdataintegrator.service.testcomponent;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.OrderDataSender;

import java.util.List;

public class TestOrderDataSender implements OrderDataSender {
    @Override
    public boolean sendOrderData(List<Order> orderList) {
        return false;
    }
}
