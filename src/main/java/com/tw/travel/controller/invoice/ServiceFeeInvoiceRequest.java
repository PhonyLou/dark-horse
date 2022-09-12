package com.tw.travel.controller.invoice;

import java.math.BigDecimal;
import java.time.Instant;

public class ServiceFeeInvoiceRequest {
    private BigDecimal amount;
    private Instant createdAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ServiceFeeInvoiceRequest(BigDecimal amount, Instant createdAt) {
        this.amount = amount;
        this.createdAt = createdAt;
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
