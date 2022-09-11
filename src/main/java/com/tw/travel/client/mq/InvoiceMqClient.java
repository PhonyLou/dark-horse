package com.tw.travel.client.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.tw.helper.DataHelper.asJsonString;

@Component
public class InvoiceMqClient {

    private final RabbitTemplate rabbitTemplate;

    private final Queue invoiceQueue;

    public InvoiceMqClient(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.invoiceQueue = queue;
    }

    public boolean issueServiceFeeInvoice(ServiceFeeInvoiceMqModel mqModel) {
        try {
            rabbitTemplate.convertAndSend(invoiceQueue.getName(), asJsonString(mqModel));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
