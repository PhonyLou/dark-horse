package com.tw.travel.service;

import java.util.Objects;

public class ServiceFeePaymentModel {
    private boolean isSuccess;

    public ServiceFeePaymentModel(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeePaymentModel that = (ServiceFeePaymentModel) o;
        return isSuccess == that.isSuccess;
    }

    @Override
    public String toString() {
        return "ServiceFeePaymentModel{" +
                "isSuccess=" + isSuccess +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess);
    }
}
