package com.tw.travel.service;

import com.tw.helper.Story;
import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.http.payment.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.payment.ServiceFeePaymentHttpClient;
import com.tw.travel.exception.InsufficientFundException;
import com.tw.travel.service.payment.ServiceFeePaymentModel;
import com.tw.travel.service.payment.ServiceFeePaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFeePaymentServiceTest {

    @Story("Story1 -> AC1 -> Example1 -> Work step 2")
    @Test
    void should_return_success_when_payServiceFee_given_payment_request_not_exists() {
        LocalDate paymentInitDate = LocalDate.parse("2022-09-12");
        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending", paymentInitDate, paymentInitDate.plusDays(5), paymentInitDate);
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);
        when(serviceFeePaymentRepo.updateStatus(1L, "success", paymentInitDate)).thenReturn(1);

        ServiceFeePaymentApiModel apiModel = new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L));
        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(apiModel)).thenReturn(true);

        ServiceFeePaymentService serviceFeePaymentService = new ServiceFeePaymentService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeePaymentService.payServiceFee(1L, BigDecimal.valueOf(1000L), paymentInitDate);

        assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_return_success_when_payServiceFee_given_payment_request_exists() {
        LocalDate paymentDate = LocalDate.parse("2022-09-12");
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending", paymentDate, paymentDate.plusDays(5), paymentDate);
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(initPaymentRequestRecord));
        when(serviceFeePaymentRepo.updateStatus(1L, "success", paymentDate)).thenReturn(1);

        ServiceFeePaymentApiModel apiModel = new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L));
        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(apiModel)).thenReturn(true);

        ServiceFeePaymentService serviceFeePaymentService = new ServiceFeePaymentService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeePaymentService.payServiceFee(1L, BigDecimal.valueOf(1000L), paymentDate);

        assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_return_success_when_payServiceFee_given_payment_success() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        ServiceFeePaymentEntity paymentRequestRecord = new ServiceFeePaymentEntity(1L, "success", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRequestRecord));

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);

        ServiceFeePaymentService serviceFeePaymentService = new ServiceFeePaymentService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeePaymentService.payServiceFee(1L, BigDecimal.valueOf(1000L), LocalDate.parse("2022-09-12"));

        assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_throw_InsufficientFundException_when_payServiceFee_given_no_sufficient_fund() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.empty());

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);
        ServiceFeePaymentEntity failedPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "failed", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.save(failedPaymentRequestRecord)).thenReturn(failedPaymentRequestRecord);

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))))
                .thenReturn(false);

        ServiceFeePaymentService serviceFeePaymentService = new ServiceFeePaymentService(serviceFeePaymentRepo, httpClient);
        assertThrows(InsufficientFundException.class,
                () -> serviceFeePaymentService.payServiceFee(1L, BigDecimal.valueOf(1000L), LocalDate.parse("2022-09-12")));
    }

    @Test
    void should_throw_InternalServerError_when_payServiceFee_given_payment_gateway_go_wrong() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.empty());

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        ServiceFeePaymentService serviceFeePaymentService = new ServiceFeePaymentService(serviceFeePaymentRepo, httpClient);
        assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> serviceFeePaymentService.payServiceFee(1L, BigDecimal.valueOf(1000L), LocalDate.parse("2022-09-12")));
    }

    @Test
    void should_throw_ResourceAccessException_when_payServiceFee_given_payment_gateway_timeout() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.empty());

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now());
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))))
                .thenThrow(ResourceAccessException.class);

        ServiceFeePaymentService serviceFeePaymentService = new ServiceFeePaymentService(serviceFeePaymentRepo, httpClient);
        assertThrows(ResourceAccessException.class,
                () -> serviceFeePaymentService.payServiceFee(1L, BigDecimal.valueOf(1000L), LocalDate.parse("2022-09-12")));
    }
}
