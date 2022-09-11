package com.tw.travel.controller.invoice;

import java.math.BigDecimal;

public class ServiceFeeInvoiceConfirmationRequest {
    private BigDecimal amount;
    private String invoiceContent;
    private String invoiceNumber;

    public ServiceFeeInvoiceConfirmationRequest(BigDecimal amount, String invoiceContent, String invoiceNumber) {
        this.amount = amount;
        this.invoiceContent = invoiceContent;
        this.invoiceNumber = invoiceNumber;
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
