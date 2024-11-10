package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.external.converter.ExternalFetchConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderDataFetcherTest {

    @Autowired
    private OrderDataFetcher orderDataFetcher;

    @Test
    @DisplayName("데이터 호출 테스트 실패 - 데이터 호출을 위한 서비스가 존재하지 않음")
    void fetchOrderData() {
        List<Order> orders = orderDataFetcher.fetchOrderData(null, ExternalFetchConverter::toOrder);

    }

}
