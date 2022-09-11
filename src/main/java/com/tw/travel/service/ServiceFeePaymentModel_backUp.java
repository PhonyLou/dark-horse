package com.tw.travel.service;

import java.math.BigDecimal;
import java.util.Objects;

public class ServiceFeePaymentModel_backUp {
    private Long travelContractId;
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeePaymentModel_backUp that = (ServiceFeePaymentModel_backUp) o;
        return Objects.equals(travelContractId, that.travelContractId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelContractId, amount);
    }

    public ServiceFeePaymentModel_backUp(Long travelContractId, BigDecimal amount) {
        this.travelContractId = travelContractId;
        this.amount = amount;
    }

    public Long getTravelContractId() {
        return travelContractId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
