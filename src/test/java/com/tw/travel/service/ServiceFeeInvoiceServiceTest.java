package com.tw.travel.service;

import com.tw.travel.client.database.invoice.ServiceFeeInvoiceEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRepo;
import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.mq.InvoiceMqClient;
import com.tw.travel.client.mq.ServiceFeeInvoiceMqModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFeeInvoiceServiceTest {

    @Test
    void should_return_true_when_issueServiceFeeInvoice_given_payment_success_and_invoice_request_accepted() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "success", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        when(invoiceMqClient.issueServiceFeeInvoice(new ServiceFeeInvoiceMqModel(1L, BigDecimal.valueOf(1000L)))).thenReturn(true);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeeInvoiceModel(true), invoiceModel);
    }

    @Test
    void should_return_false_when_issueServiceFeeInvoice_given_payment_is_pending() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "pending", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeeInvoiceModel(false), invoiceModel);
    }

    @Test
    void should_return_false_when_issueServiceFeeInvoice_given_payment_is_failed() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "failed", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeeInvoiceModel(false), invoiceModel);
    }

    @Test
    void should_return_true_when_storeServiceFeeInvoice_given_invoice_gateway_callback() {
        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeeInvoiceRepo serviceFeeInvoiceRepo = mock(ServiceFeeInvoiceRepo.class);
        ServiceFeeInvoiceEntity entity = new ServiceFeeInvoiceEntity(1L, BigDecimal.valueOf(1000L), "sample-content", "3-12345");
        when(serviceFeeInvoiceRepo.save(entity)).thenReturn(entity);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, serviceFeeInvoiceRepo);
        ServiceFeeInvoiceModel invoiceModel = service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(1000L), "3-12345");

        assertEquals(new ServiceFeeInvoiceModel(true), invoiceModel);
    }
}
