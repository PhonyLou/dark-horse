package com.tw.travel.client.database.payment;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "service_fee_payment")
public class ServiceFeePaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelContractId;

    private String status;
    private LocalDate createdAt;
    private LocalDate expiredAt;
    private LocalDate lastUpdate;

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDate expiredAt) {
        this.expiredAt = expiredAt;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ServiceFeePaymentEntity{" +
                "travelContractId=" + travelContractId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeePaymentEntity that = (ServiceFeePaymentEntity) o;
        return Objects.equals(travelContractId, that.travelContractId) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt) && Objects.equals(expiredAt, that.expiredAt) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelContractId, status, createdAt, expiredAt, lastUpdate);
    }

    public ServiceFeePaymentEntity(Long travelContractId, String status, LocalDate createdAt, LocalDate expiredAt, LocalDate lastUpdate) {
        this.travelContractId = travelContractId;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.lastUpdate = lastUpdate;
    }

    protected ServiceFeePaymentEntity() {
    }
}
