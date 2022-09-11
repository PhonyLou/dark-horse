package com.tw.travel.service;

import com.tw.travel.client.database.ServiceFeePaymentEntity;
import com.tw.travel.client.database.ServiceFeePaymentRepo;
import com.tw.travel.client.http.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.ServiceFeePaymentHttpClient;
import com.tw.travel.exception.InsufficientFundException;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceFeeServiceTest {

    @Test
    void should_return_success_when_payServiceFee_given_payment_request_not_exists() {
        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending");
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);
        ServiceFeePaymentEntity successRecord = new ServiceFeePaymentEntity(1L, "success");
        when(serviceFeePaymentRepo.save(successRecord)).thenReturn(successRecord);

        ServiceFeePaymentApiModel apiModel = new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L));
        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(apiModel)).thenReturn(true);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_return_success_when_payServiceFee_given_payment_request_exists() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending");
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(initPaymentRequestRecord));

        ServiceFeePaymentEntity successRecord = new ServiceFeePaymentEntity(1L, "success");
        when(serviceFeePaymentRepo.save(successRecord)).thenReturn(successRecord);

        ServiceFeePaymentApiModel apiModel = new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L));
        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(apiModel)).thenReturn(true);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_return_success_when_payServiceFee_given_payment_success() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        ServiceFeePaymentEntity paymentRequestRecord = new ServiceFeePaymentEntity(1L, "success");
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRequestRecord));

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L));

        assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_throw_InsufficientFundException_when_payServiceFee_given_no_sufficient_fund() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.empty());

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending");
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);
        ServiceFeePaymentEntity failedPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "failed");
        when(serviceFeePaymentRepo.save(failedPaymentRequestRecord)).thenReturn(failedPaymentRequestRecord);

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))))
                .thenReturn(false);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        assertThrows(InsufficientFundException.class,
                () -> serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L)));
    }

    @Test
    void should_throw_InternalServerError_when_payServiceFee_given_payment_gateway_go_wrong() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.empty());

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending");
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L)));
    }

    @Test
    void should_throw_ResourceAccessException_when_payServiceFee_given_payment_gateway_timeout() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.empty());

        ServiceFeePaymentEntity initPaymentRequestRecord = new ServiceFeePaymentEntity(1L, "pending");
        when(serviceFeePaymentRepo.save(initPaymentRequestRecord)).thenReturn(initPaymentRequestRecord);

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);
        when(httpClient.payServiceFee(new ServiceFeePaymentApiModel(1L, BigDecimal.valueOf(1000L))))
                .thenThrow(ResourceAccessException.class);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        assertThrows(ResourceAccessException.class,
                () -> serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L)));
    }
}
