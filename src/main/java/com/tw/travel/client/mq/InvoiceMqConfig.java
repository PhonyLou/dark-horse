package com.tw.travel.client.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceMqConfig {

    @Value("${queue.name}")
    private String message;

    @Bean
    public Queue invoiceQueue() {
        return new Queue(message, true);
    }

}
