package com.tw.travel.client.http.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceFeePaymentHttpClient {

    private RestTemplate restTemplate;

    private PaymentGateway paymentGateway;

    public ServiceFeePaymentHttpClient(RestTemplate restTemplate, PaymentGateway paymentGateway) {
        this.restTemplate = restTemplate;
        this.paymentGateway = paymentGateway;
    }

    public boolean payServiceFee(final ServiceFeePaymentApiModel apiModel) {
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(paymentGateway.getHost() + "/service-fee-payment", apiModel, String.class);
            return stringResponseEntity.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            return false;
        }
    }
}
