package com.tw.travel.client.http;

import com.tw.travel.client.http.payment.PaymentGateway;
import com.tw.travel.client.http.payment.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.payment.ServiceFeePaymentHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(PaymentGatewayStubExtension.class)
public class ServiceFeePaymentHttpClientTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentGateway paymentGateway;

    @Test
    void should_return_true_when_payServiceFee_given_payment_gateway_returns_200(PaymentGatewayStub paymentGatewayStub) {
        paymentGatewayStub.stubPaymentGateway("/service-fee-payment", 200, 0);

        ServiceFeePaymentHttpClient httpClient = new ServiceFeePaymentHttpClient(restTemplate, paymentGateway);
        boolean isPaymentSuccess = httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L)));
        Assertions.assertTrue(isPaymentSuccess);
    }

    @Test
    void should_return_false_when_payServiceFee_given_payment_gateway_returns_400(PaymentGatewayStub paymentGatewayStub) {
        paymentGatewayStub.stubPaymentGateway("/service-fee-payment", 400, 0);

        ServiceFeePaymentHttpClient httpClient = new ServiceFeePaymentHttpClient(restTemplate, paymentGateway);
        boolean isPaymentSuccess = httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L)));
        Assertions.assertFalse(isPaymentSuccess);
    }

    @Test
    void should_throw_InternalServerError_when_payServiceFee_given_payment_gateway_returns_server_side_error(PaymentGatewayStub paymentGatewayStub) {
        paymentGatewayStub.stubPaymentGateway("/service-fee-payment", 500, 0);

        ServiceFeePaymentHttpClient httpClient = new ServiceFeePaymentHttpClient(restTemplate, paymentGateway);
        assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))));
    }

    @Test
    void should_throw_InternalServerError_when_payServiceFee_given_timeout(PaymentGatewayStub paymentGatewayStub) {
        paymentGatewayStub.stubPaymentGateway("/service-fee-payment", 500, 200);

        ServiceFeePaymentHttpClient httpClient = new ServiceFeePaymentHttpClient(restTemplate, paymentGateway);
        assertThrows(ResourceAccessException.class,
                () -> httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))));
    }
}
