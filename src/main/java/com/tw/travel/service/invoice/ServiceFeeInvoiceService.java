package com.tw.travel.service.invoice;

import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.mq.InvoiceMqClient;
import com.tw.travel.client.mq.ServiceFeeInvoiceMqModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ServiceFeeInvoiceService {
    private final ServiceFeePaymentRepo serviceFeePaymentRepo;
    private final InvoiceMqClient invoiceMqClient;

    public ServiceFeeInvoiceService(ServiceFeePaymentRepo serviceFeePaymentRepo, InvoiceMqClient invoiceMqClient) {
        this.serviceFeePaymentRepo = serviceFeePaymentRepo;
        this.invoiceMqClient = invoiceMqClient;
    }

    public ServiceFeeInvoiceModel issueServiceFeeInvoice(Long travelContractId, BigDecimal amount) {
        Optional<ServiceFeePaymentEntity> successRecord = serviceFeePaymentRepo
                .findById(travelContractId)
                .filter(p -> p.getStatus().equalsIgnoreCase("success"));
        if (successRecord.isPresent()) {
            boolean sendMqSuccess = invoiceMqClient.issueServiceFeeInvoice(new ServiceFeeInvoiceMqModel(travelContractId, amount));
            return new ServiceFeeInvoiceModel(sendMqSuccess);
        }

        return new ServiceFeeInvoiceModel(false);
    }
}
