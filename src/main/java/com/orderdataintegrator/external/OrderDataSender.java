package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;

import java.util.List;

public interface OrderDataSender {

    boolean sendOrderData(List<Order> orderList);
}
