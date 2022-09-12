package com.tw.travel.client.database.invoice;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "service_fee_invoice_request")
public class ServiceFeeInvoiceRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelContractId;

    private String status;
    private Instant createdAt;
    private Instant expiredAt;
    private Instant lastUpdate;

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
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
        ServiceFeeInvoiceRequestEntity that = (ServiceFeeInvoiceRequestEntity) o;
        return Objects.equals(travelContractId, that.travelContractId) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt) && Objects.equals(expiredAt, that.expiredAt) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelContractId, status, createdAt, expiredAt, lastUpdate);
    }

    public ServiceFeeInvoiceRequestEntity(Long travelContractId, String status, Instant createdAt, Instant expiredAt, Instant lastUpdate) {
        this.travelContractId = travelContractId;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.lastUpdate = lastUpdate;
    }

    protected ServiceFeeInvoiceRequestEntity() {
    }
}
