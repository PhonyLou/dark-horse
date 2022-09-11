package com.tw.travel.client.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(PaymentGatewayStubExtension.class)
public class ServiceFeePaymentHttpClientTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void should_return_true_when_payServiceFee_given_payment_gateway_returns_200(PaymentGatewayStub paymentGatewayStub) {
        paymentGatewayStub.stubPaymentGateway("/service-fee-payment", 200);

        ServiceFeePaymentHttpClient httpClient = new ServiceFeePaymentHttpClient(restTemplate);
        boolean isPaymentSuccess = httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L)));
        Assertions.assertTrue(isPaymentSuccess);
    }

    @Test
    void should_return_false_when_payServiceFee_given_payment_gateway_returns_400(PaymentGatewayStub paymentGatewayStub) {
        paymentGatewayStub.stubPaymentGateway("/service-fee-payment", 400);

        ServiceFeePaymentHttpClient httpClient = new ServiceFeePaymentHttpClient(restTemplate);
        boolean isPaymentSuccess = httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L)));
        Assertions.assertFalse(isPaymentSuccess);
    }
}
