package com.tw.travel.service;

import com.tw.travel.client.database.ServiceFeePaymentEntity;
import com.tw.travel.client.database.ServiceFeePaymentRepo;
import com.tw.travel.client.http.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.ServiceFeePaymentHttpClient;
import com.tw.travel.exception.InsufficientFundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ServiceFeeService {
    private ServiceFeePaymentRepo serviceFeePaymentRepo;

    private ServiceFeePaymentHttpClient httpClient;

    public ServiceFeeService(ServiceFeePaymentRepo serviceFeePaymentRepo, ServiceFeePaymentHttpClient httpClient) {
        this.serviceFeePaymentRepo = serviceFeePaymentRepo;
        this.httpClient = httpClient;
    }

    public ServiceFeePaymentModel payServiceFee(Long travelContractId, BigDecimal amount) {
        Optional<ServiceFeePaymentEntity> paymentRecord = serviceFeePaymentRepo.findById(travelContractId);
        Optional<ServiceFeePaymentEntity> successRecord = paymentRecord.filter(p -> p.getStatus().equalsIgnoreCase("success"));
        if (successRecord.isPresent()) {
            return new ServiceFeePaymentModel(true);
        }

        if (!paymentRecord.isPresent()) {
            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "pending"));
        }

        if (httpClient.payServiceFee(new ServiceFeePaymentApiModel(travelContractId, amount))) {
            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "success"));
            return new ServiceFeePaymentModel(true);
        } else {
            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "failed"));
            throw new InsufficientFundException();
        }
    }
}
