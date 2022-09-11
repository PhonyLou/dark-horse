package com.tw.travel.service.invoice;

import java.util.Objects;

public class ServiceFeeInvoiceModel {
    private boolean isSuccess;

    public ServiceFeeInvoiceModel(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeeInvoiceModel that = (ServiceFeeInvoiceModel) o;
        return isSuccess == that.isSuccess;
    }

    @Override
    public String toString() {
        return "ServiceFeeInvoiceModel{" +
                "isSuccess=" + isSuccess +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess);
    }
}
