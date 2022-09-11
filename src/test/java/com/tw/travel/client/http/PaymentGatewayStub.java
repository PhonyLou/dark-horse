package com.tw.travel.client.http;

public interface PaymentGatewayStub {
    void stubPaymentGateway(String url, Integer statusCode, Integer delaySeconds);
}
