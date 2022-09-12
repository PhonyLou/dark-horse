package com.tw.travel.service.invoice;

import com.tw.travel.client.database.invoice.ServiceFeeInvoiceEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRepo;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRequestEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRequestRepo;
import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.mq.InvoiceMqClient;
import com.tw.travel.client.mq.ServiceFeeInvoiceMqModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class ServiceFeeInvoiceService {
    private final ServiceFeePaymentRepo serviceFeePaymentRepo;
    private final ServiceFeeInvoiceRepo serviceFeeInvoiceRepo;
    private final ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo;
    private final InvoiceMqClient invoiceMqClient;

    public ServiceFeeInvoiceService(ServiceFeePaymentRepo serviceFeePaymentRepo, InvoiceMqClient invoiceMqClient, ServiceFeeInvoiceRepo serviceFeeInvoiceRepo, ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo) {
        this.serviceFeePaymentRepo = serviceFeePaymentRepo;
        this.invoiceMqClient = invoiceMqClient;
        this.serviceFeeInvoiceRepo = serviceFeeInvoiceRepo;
        this.serviceFeeInvoiceRequestRepo = serviceFeeInvoiceRequestRepo;
    }

    public ServiceFeeInvoiceModel issueServiceFeeInvoice(Long travelContractId, BigDecimal amount, Instant createdAt) {
        Optional<ServiceFeePaymentEntity> successRecord = serviceFeePaymentRepo
                .findById(travelContractId)
                .filter(p -> p.getStatus().equalsIgnoreCase("success"));
        if (successRecord.isPresent()) {
            Optional<ServiceFeeInvoiceRequestEntity> invoiceRequest = serviceFeeInvoiceRequestRepo.findById(travelContractId);
            if (invoiceRequest.isPresent()) {
                return new ServiceFeeInvoiceModel(true);
            }

            boolean sendMqSuccess = invoiceMqClient.issueServiceFeeInvoice(new ServiceFeeInvoiceMqModel(travelContractId, amount));
            serviceFeeInvoiceRequestRepo.save(new ServiceFeeInvoiceRequestEntity(1L, "pending", createdAt, createdAt.plusSeconds(24 * 60 * 60), createdAt));
            return new ServiceFeeInvoiceModel(sendMqSuccess);
        }

        return new ServiceFeeInvoiceModel(false);
    }

    public ServiceFeeInvoiceModel storeServiceFeeInvoice(Long travelContractId, String invoiceContent, BigDecimal amount, String invoiceNumber, Instant createdAt) {
        serviceFeeInvoiceRepo.save(new ServiceFeeInvoiceEntity(travelContractId, amount, invoiceContent, invoiceNumber));
        Integer successUpdated = serviceFeeInvoiceRequestRepo.updateStatus(travelContractId, "success", createdAt);
        return new ServiceFeeInvoiceModel(successUpdated == 1);
    }
}
