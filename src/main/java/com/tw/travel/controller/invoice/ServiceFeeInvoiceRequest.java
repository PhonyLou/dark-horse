package com.tw.travel.controller.invoice;

import java.math.BigDecimal;

public class ServiceFeeInvoiceRequest {
    private BigDecimal amount;

    public ServiceFeeInvoiceRequest(BigDecimal amount) {
        this.amount = amount;
    }
    public ServiceFeeInvoiceRequest() {
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
