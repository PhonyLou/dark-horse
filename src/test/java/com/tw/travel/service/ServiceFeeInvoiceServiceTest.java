package com.tw.travel.service;

import com.tw.helper.Story;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRepo;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRequestEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.mq.InvoiceMqClient;
import com.tw.travel.client.mq.ServiceFeeInvoiceMqModel;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRequestRepo;
import com.tw.travel.service.invoice.ServiceFeeInvoiceModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFeeInvoiceServiceTest {

    @Story("Story2 -> AC1 -> Example1 -> Work step 2")
    @Test
    void should_return_true_when_issueServiceFeeInvoice_given_payment_success_and_invoice_request_accepted() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        LocalDate paymentSuccessDate = LocalDate.parse("2022-08-20");
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "success", paymentSuccessDate, paymentSuccessDate.plusDays(5), paymentSuccessDate);
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        when(invoiceMqClient.issueServiceFeeInvoice(new ServiceFeeInvoiceMqModel(1L, BigDecimal.valueOf(1000L)))).thenReturn(true);

        ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo = mock(ServiceFeeInvoiceRequestRepo.class);
        Instant invoiceRequestCreatedAt = Instant.parse("2022-08-25T15:30:00Z");
        ServiceFeeInvoiceRequestEntity serviceFeeInvoiceRequestEntity = new ServiceFeeInvoiceRequestEntity(1L, "pending", invoiceRequestCreatedAt, invoiceRequestCreatedAt.plusSeconds(24 * 60 * 60), invoiceRequestCreatedAt);
        when(serviceFeeInvoiceRequestRepo.save(serviceFeeInvoiceRequestEntity)).thenReturn(serviceFeeInvoiceRequestEntity);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null, serviceFeeInvoiceRequestRepo);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), invoiceRequestCreatedAt);

        assertEquals(new ServiceFeeInvoiceModel(true), invoiceModel);
    }

    @Story("Story2 -> AC1 -> Example2 -> Work step 2")
    @Test
    void should_return_true_when_issueServiceFeeInvoice_given_payment_success_and_invoice_request_exists() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        LocalDate paymentSuccessDate = LocalDate.parse("2022-08-20");
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "success", paymentSuccessDate, paymentSuccessDate.plusDays(5), paymentSuccessDate);
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo = mock(ServiceFeeInvoiceRequestRepo.class);

        Instant previousInvoiceRequestCreatedAt = Instant.parse("2022-08-24T19:30:00Z");
        when(serviceFeeInvoiceRequestRepo.findById(1L)).thenReturn(Optional.of(new ServiceFeeInvoiceRequestEntity(1L, "pending", previousInvoiceRequestCreatedAt, previousInvoiceRequestCreatedAt.plusSeconds(24 * 60 * 60), previousInvoiceRequestCreatedAt)));

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null, serviceFeeInvoiceRequestRepo);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), Instant.parse("2022-08-25T15:30:00Z"));

        assertEquals(new ServiceFeeInvoiceModel(true), invoiceModel);
    }

    @Story("Story2 -> AC2 -> Example1 -> Work step 2")
    @Test
    void should_return_false_when_issueServiceFeeInvoice_given_payment_is_pending() {
        LocalDate paymentDate = LocalDate.parse("2022-08-20");
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "pending", paymentDate, paymentDate.plusDays(5), paymentDate);
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo = mock(ServiceFeeInvoiceRequestRepo.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null, serviceFeeInvoiceRequestRepo);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), Instant.parse("2022-08-25T15:30:00Z"));

        assertEquals(new ServiceFeeInvoiceModel(false), invoiceModel);
    }

    @Test
    void should_return_false_when_issueServiceFeeInvoice_given_payment_is_failed() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeePaymentEntity paymentRecord = new ServiceFeePaymentEntity(1L, "failed", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRecord));

        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo = mock(ServiceFeeInvoiceRequestRepo.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, null, serviceFeeInvoiceRequestRepo);
        ServiceFeeInvoiceModel invoiceModel = service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), Instant.now());

        assertEquals(new ServiceFeeInvoiceModel(false), invoiceModel);
    }

    @Test
    void should_return_true_when_storeServiceFeeInvoice_given_invoice_gateway_callback() {
        InvoiceMqClient invoiceMqClient = mock(InvoiceMqClient.class);
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        ServiceFeeInvoiceRepo serviceFeeInvoiceRepo = mock(ServiceFeeInvoiceRepo.class);
        ServiceFeeInvoiceEntity entity = new ServiceFeeInvoiceEntity(1L, BigDecimal.valueOf(1000L), "sample-content", "3-12345");
        when(serviceFeeInvoiceRepo.save(entity)).thenReturn(entity);

        ServiceFeeInvoiceRequestRepo serviceFeeInvoiceRequestRepo = mock(ServiceFeeInvoiceRequestRepo.class);

        ServiceFeeInvoiceService service = new ServiceFeeInvoiceService(serviceFeePaymentRepo, invoiceMqClient, serviceFeeInvoiceRepo, serviceFeeInvoiceRequestRepo);
        ServiceFeeInvoiceModel invoiceModel = service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(1000L), "3-12345");

        assertEquals(new ServiceFeeInvoiceModel(true), invoiceModel);
    }
}
