package com.orderdataintegrator.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalFetchRequestQuery {

    private String q;
    private String sort, order, per_page, page; // query parameters

    public ExternalFetchRequestQuery(String q) {
        this.q = q;
    }

}
