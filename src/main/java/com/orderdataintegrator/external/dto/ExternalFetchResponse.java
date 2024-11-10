package com.orderdataintegrator.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalFetchResponse {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("etc")
    private String etc;


}
