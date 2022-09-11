package com.tw.travel.client.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class ServiceFeePaymentHttpClient {

    private RestTemplate restTemplate;

    @Resource
    private Environment env;

    @Value("${payment-gateway.host}")
    private String gatewayHost;

    public ServiceFeePaymentHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean payServiceFee(final ServiceFeePaymentApiModel apiModel) {
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8024/service-fee-payment", apiModel, String.class);
            System.out.println("code ===========>" + stringResponseEntity.getStatusCode().value());
            return stringResponseEntity.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            return false;
        }
    }
}
