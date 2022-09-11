package com.tw.travel.service;

import com.tw.travel.client.database.ServiceFeePaymentEntity;
import com.tw.travel.client.database.ServiceFeePaymentRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ServiceFeeService {
    private ServiceFeePaymentRepo serviceFeePaymentRepo;

    public ServiceFeeService(ServiceFeePaymentRepo serviceFeePaymentRepo) {
        this.serviceFeePaymentRepo = serviceFeePaymentRepo;
    }


    public ServiceFeePaymentModel payServiceFee(Long travelContractId, BigDecimal amount) {
        serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "pending"));
        return new ServiceFeePaymentModel(true);
    }
}
