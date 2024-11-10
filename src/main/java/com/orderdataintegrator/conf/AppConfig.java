package com.orderdataintegrator.conf;

import com.orderdataintegrator.external.HttpOrderDataFetcher;
import com.orderdataintegrator.external.HttpOrderDataSender;
import com.orderdataintegrator.external.OrderDataSender;
import com.orderdataintegrator.repository.InMemoryOrderRepository;
import com.orderdataintegrator.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${order.fetch.url:https://test/order/fetch}")
    private String orderFetchUrl;

    @Value("${order.send.url:https://test/order/send}")
    private String orderSendUrl;

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public HttpOrderDataFetcher httpOrderDataFetcher(RestTemplate restTemplate) {
        return new HttpOrderDataFetcher(restTemplate, orderFetchUrl);
    }

    @Bean
    public OrderDataSender orderDataSender(RestTemplate restTemplate) {
        return new HttpOrderDataSender(restTemplate, orderSendUrl);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}
