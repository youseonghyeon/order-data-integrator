package com.orderdataintegrator.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orderdataintegrator.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExternalSenderRequest {
    @JsonProperty("id")
    private String id;
    @JsonProperty("orders")
    private List<Order> orders;
}
