package com.tw.travel.client.mq;

import java.math.BigDecimal;
import java.util.Objects;

public class ServiceFeeInvoiceMqModel {
    private Long travelContractId;
    private BigDecimal amount;

    public ServiceFeeInvoiceMqModel(Long travelContractId, BigDecimal amount) {
        this.travelContractId = travelContractId;
        this.amount = amount;
    }

    public Long getTravelContractId() {
        return travelContractId;
    }

    public void setTravelContractId(Long travelContractId) {
        this.travelContractId = travelContractId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ServiceFeeInvoiceMqModel{" +
                "travelContractId=" + travelContractId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeeInvoiceMqModel that = (ServiceFeeInvoiceMqModel) o;
        return Objects.equals(travelContractId, that.travelContractId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelContractId, amount);
    }

    public ServiceFeeInvoiceMqModel() {
    }
}
