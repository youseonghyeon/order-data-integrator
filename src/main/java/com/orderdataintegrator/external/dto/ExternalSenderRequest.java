package com.orderdataintegrator.external.dto;

import com.orderdataintegrator.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExternalSenderRequest {
    private String id;
    private List<Order> orders;
}
