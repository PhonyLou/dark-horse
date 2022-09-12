package com.tw.travel.service.payment;

import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import com.tw.travel.client.http.payment.ServiceFeePaymentApiModel;
import com.tw.travel.client.http.payment.ServiceFeePaymentHttpClient;
import com.tw.travel.exception.InsufficientFundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ServiceFeePaymentService {
    private ServiceFeePaymentRepo serviceFeePaymentRepo;

    private ServiceFeePaymentHttpClient httpClient;

    public ServiceFeePaymentService(ServiceFeePaymentRepo serviceFeePaymentRepo, ServiceFeePaymentHttpClient httpClient) {
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
            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "pending", LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now()));
        }

        if (httpClient.payServiceFee(new ServiceFeePaymentApiModel(travelContractId, amount))) {
            serviceFeePaymentRepo.updateStatus(travelContractId, "success", LocalDate.now());
//            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "success", null, null, LocalDate.now()));
            return new ServiceFeePaymentModel(true);
        } else {
            serviceFeePaymentRepo.updateStatus(travelContractId, "failed", LocalDate.now());
//            serviceFeePaymentRepo.save(new ServiceFeePaymentEntity(travelContractId, "failed", null, null, LocalDate.now()));
            throw new InsufficientFundException();
        }
    }
}
