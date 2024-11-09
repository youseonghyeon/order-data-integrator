package com.orderdataintegrator.conf;

import com.orderdataintegrator.external.HttpOrderDataFetcher;
import com.orderdataintegrator.repository.InMemoryOrderRepository;
import com.orderdataintegrator.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public HttpOrderDataFetcher httpOrderDataFetcher() {
        return new HttpOrderDataFetcher();
    }
}
