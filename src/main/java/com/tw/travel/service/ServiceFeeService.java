package com.tw.travel.service;

import com.tw.travel.client.database.ServiceFeePaymentEntity;
import com.tw.travel.client.database.ServiceFeePaymentRepo;
import com.tw.travel.client.http.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.ServiceFeePaymentHttpClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ServiceFeeService {
    private ServiceFeePaymentRepo serviceFeePaymentRepo;

    private ServiceFeePaymentHttpClient httpClient;

    public ServiceFeeService(ServiceFeePaymentRepo serviceFeePaymentRepo, ServiceFeePaymentHttpClient httpClient) {
        this.serviceFeePaymentRepo = serviceFeePaymentRepo;
        this.httpClient = httpClient;
    }

    public ServiceFeePaymentModel payServiceFee(Long travelContractId, BigDecimal amount) {
        serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "pending"));
        if (httpClient.payServiceFee(new ServiceFeePaymentApiModel(travelContractId, amount))) {
            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "success"));
            return new ServiceFeePaymentModel(true);
        } else {
            return new ServiceFeePaymentModel(false);
        }
    }
}
