package com.tw.travel.client.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "service_fee_payment")
public class ServiceFeePaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelContractId;

    private String status;

    protected ServiceFeePaymentEntity() {
    }

    @Override
    public String toString() {
        return "ServiceFeePaymentEntity{" +
                "travelContractId=" + travelContractId +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeePaymentEntity that = (ServiceFeePaymentEntity) o;
        return Objects.equals(travelContractId, that.travelContractId) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelContractId, status);
    }

    public Long getTravelContractId() {
        return travelContractId;
    }

    public void setTravelContractId(Long travelContractId) {
        this.travelContractId = travelContractId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceFeePaymentEntity(Long travelContractId, String status) {
        this.travelContractId = travelContractId;
        this.status = status;
    }
}
