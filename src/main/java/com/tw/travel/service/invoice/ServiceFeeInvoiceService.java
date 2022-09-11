package com.tw.travel.service.invoice;

import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ServiceFeeInvoiceService {
    private ServiceFeePaymentRepo serviceFeePaymentRepo;

    public ServiceFeeInvoiceModel issueServiceFeeInvoice(Long travelContractId, BigDecimal amount) {
        return null;
    }
}
