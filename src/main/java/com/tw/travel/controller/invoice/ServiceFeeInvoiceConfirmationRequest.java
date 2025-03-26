package com.tw.travel.controller.invoice;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

public class ServiceFeeInvoiceConfirmationRequest {
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @DecimalMax(value = "100000000", message = "Amount exceeds maximum allowed value")
    @Digits(integer = 9, fraction = 2, message = "Amount must have at most 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "Invoice content is required")
    @Size(min = 10, message = "Invoice content must be at least 10 characters")
    private String invoiceContent;

    @NotBlank(message = "Invoice number cannot be empty")
    private String invoiceNumber;

    @NotNull(message = "CreatedAt is required")
    private Instant createdAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ServiceFeeInvoiceConfirmationRequest(BigDecimal amount, String invoiceContent, String invoiceNumber, Instant createdAt) {
        this.amount = amount;
        this.invoiceContent = invoiceContent;
        this.invoiceNumber = invoiceNumber;
        this.createdAt = createdAt;
    }

    public ServiceFeeInvoiceConfirmationRequest() {
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
