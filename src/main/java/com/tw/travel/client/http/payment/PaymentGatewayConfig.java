package com.tw.travel.client.http.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfig {

    @Value("${payment-gateway.host}")
    private String host;

    @Bean
    public PaymentGateway paymentGateway() {
        return new PaymentGateway(host);
    }

}
