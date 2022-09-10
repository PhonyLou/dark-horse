package com.tw.travel.controller;

import java.util.Objects;

public class ServiceFeePaymentDTO {
    private String message;

    public ServiceFeePaymentDTO() {
    }
    public ServiceFeePaymentDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeePaymentDTO that = (ServiceFeePaymentDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public String toString() {
        return "ServiceFeePaymentDTO{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
