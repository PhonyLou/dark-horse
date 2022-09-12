package com.tw.travel.controller.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceFeePaymentRequest {
    private BigDecimal amount;
    private LocalDate createdAt;

    public ServiceFeePaymentRequest() {
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ServiceFeePaymentRequest(BigDecimal amount, LocalDate createdAt) {
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
