package com.orderdataintegrator.external;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;

@SpringBootTest
class OrderDataFetcherTest {

    @Autowired
    private OrderDataFetcher<Void> orderDataFetcher;

    @Test
    @DisplayName("데이터 호출 테스트 실패 - 데이터 호출을 위한 서비스가 존재하지 않음")
    void fetchOrderData() {
        Assertions.assertThrows(ResourceAccessException.class, () -> orderDataFetcher.fetchOrderData(null));
    }

}
