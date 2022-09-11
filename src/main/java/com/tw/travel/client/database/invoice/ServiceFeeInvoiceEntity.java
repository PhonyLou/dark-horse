package com.tw.travel.client.database.invoice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "service_fee_invoice")
public class ServiceFeeInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelContractId;

    private BigDecimal amount;
    private String invoiceContent;
    private String invoiceNumber;

    protected ServiceFeeInvoiceEntity() {
    }

    @Override
    public String toString() {
        return "ServiceFeeInvoiceEntity{" +
                "travelContractId=" + travelContractId +
                ", amount=" + amount +
                ", invoiceContent='" + invoiceContent + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceFeeInvoiceEntity that = (ServiceFeeInvoiceEntity) o;
        return Objects.equals(travelContractId, that.travelContractId) && Objects.equals(amount, that.amount) && Objects.equals(invoiceContent, that.invoiceContent) && Objects.equals(invoiceNumber, that.invoiceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelContractId, amount, invoiceContent, invoiceNumber);
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

    public ServiceFeeInvoiceEntity(Long travelContractId, BigDecimal amount, String invoiceContent, String invoiceNumber) {
        this.travelContractId = travelContractId;
        this.amount = amount;
        this.invoiceContent = invoiceContent;
        this.invoiceNumber = invoiceNumber;
    }
}
