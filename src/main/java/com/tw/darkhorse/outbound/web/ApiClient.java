package com.tw.darkhorse.outbound.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiClient {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getABC() {
        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);
        ResponseEntity<String> exchange = restTemplate.exchange("http://demo.com:8086/abc", HttpMethod.GET, requestEntity, String.class);
        return exchange;
    }
}
