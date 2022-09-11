package com.tw.travel.controller.invoice;

import java.util.Objects;

public class ServiceFeeInvoiceDTO {
    private String message;

    public ServiceFeeInvoiceDTO(String message) {
        this.message = message;
    }

    public ServiceFeeInvoiceDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServiceFeeInvoiceDTO{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeeInvoiceDTO that = (ServiceFeeInvoiceDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
