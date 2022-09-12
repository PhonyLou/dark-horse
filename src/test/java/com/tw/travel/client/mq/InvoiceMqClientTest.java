package com.tw.travel.client.mq;

import com.tw.helper.Story;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InvoiceMqClientTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue invoiceQueue;

    @Story("Story2 -> AC1 -> Example1 -> Work step 4")
    @Test
    void should_return_true_when_issueServiceFeeInvoice_given_message_sent_to_mq() {
        InvoiceMqClient invoiceMqClient = new InvoiceMqClient(rabbitTemplate, invoiceQueue);

        boolean sendResult = invoiceMqClient.issueServiceFeeInvoice(new ServiceFeeInvoiceMqModel(1L, BigDecimal.valueOf(1000L)));
        assertTrue(sendResult);
    }
}
