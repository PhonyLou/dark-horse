package com.tw.travel.service;

import com.tw.travel.client.database.ServiceFeePaymentEntity;
import com.tw.travel.client.database.ServiceFeePaymentRepo;
import com.tw.travel.client.http.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.ServiceFeePaymentHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

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

        Assertions.assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
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

        Assertions.assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }

    @Test
    void should_return_success_when_payServiceFee_given_payment_success() {
        ServiceFeePaymentRepo serviceFeePaymentRepo = mock(ServiceFeePaymentRepo.class);

        ServiceFeePaymentEntity paymentRequestRecord = new ServiceFeePaymentEntity(1L, "success");
        when(serviceFeePaymentRepo.findById(1L)).thenReturn(Optional.of(paymentRequestRecord));

        ServiceFeePaymentHttpClient httpClient = mock(ServiceFeePaymentHttpClient.class);

        ServiceFeeService serviceFeeService = new ServiceFeeService(serviceFeePaymentRepo, httpClient);
        ServiceFeePaymentModel serviceFeePaymentModel = serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L));

        Assertions.assertEquals(new ServiceFeePaymentModel(true), serviceFeePaymentModel);
    }
}
