package com.orderdataintegrator.external;

import com.orderdataintegrator.entity.Order;
import com.orderdataintegrator.entity.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class HttpOrderDataFetcher implements OrderDataFetcher<Void> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Order> fetchOrderData(Optional<Void> input) {
        ExternalResponse[] forObject = restTemplate.getForObject("https://order-external-service/orders", ExternalResponse[].class);
        return Arrays.stream(forObject).map(ExternalResponse::toOrder).toList();
    }

    class ExternalResponse {
        Long orderId;
        String customerName;
        LocalDateTime orderDate;
        OrderStatus orderStatus;
        String etc;

        protected static Order toOrder(ExternalResponse externalResponse) {
            return new Order(externalResponse.orderId, externalResponse.customerName, externalResponse.orderDate, externalResponse.orderStatus);
        }
    }


}
