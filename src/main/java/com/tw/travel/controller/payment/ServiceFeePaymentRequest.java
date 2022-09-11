package com.tw.travel.controller.payment;

import java.math.BigDecimal;

public class ServiceFeePaymentRequest {
    private BigDecimal amount;

    public ServiceFeePaymentRequest(BigDecimal amount) {
        this.amount = amount;
    }
    public ServiceFeePaymentRequest() {
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
