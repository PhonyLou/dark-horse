package com.tw.travel.controller.invoice;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

public class ServiceFeeInvoiceRequest {
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @DecimalMax(value = "100000000", message = "Amount exceeds maximum allowed value")
    @Digits(integer = 9, fraction = 2, message = "Amount must have at most 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "CreatedAt is required")
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
