package com.tw.travel.service;

import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.mq.InvoiceMqClient;
import com.tw.travel.client.mq.ServiceFeeInvoiceMqModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFeeInvoiceServiceTest {

    @Test
    void should_return_true_when_issueServiceFeeInvoice_given_payment_success_and_invoice_request_accepted() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "success");
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        when(invoiceMqClient.issueServiceFeeInvoice(new ServiceFeeInvoiceMqModel(1L, BigDecimal.valueOf(1000L)))).thenReturn(true);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeeInvoiceModel(true), invoiceModel);
    }

    @Test
    void should_return_false_when_issueServiceFeeInvoice_given_payment_is_pending() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "pending");
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeeInvoiceModel(false), invoiceModel);
    }

    @Test
    void should_return_false_when_issueServiceFeeInvoice_given_payment_is_failed() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "failed");
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeeInvoiceModel(false), invoiceModel);
    }
}
