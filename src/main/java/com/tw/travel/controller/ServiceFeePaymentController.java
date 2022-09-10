package com.tw.travel.controller;

import com.tw.travel.service.ServiceFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceFeePaymentController {

    private final ServiceFeeService serviceFeeService;

    public ServiceFeePaymentController(final ServiceFeeService serviceFeeService) {
        this.serviceFeeService = serviceFeeService;
    }

    @PostMapping("/travel-contracts/{tid}/service-fee-payments")
    final public ResponseEntity payServiceFee(@PathVariable("tid") Long travelContractId, @RequestBody ServiceFeePaymentRequest req) {
        serviceFeeService.payServiceFee(travelContractId, req.getAmount());
        return ResponseEntity.ok(new ServiceFeePaymentDTO("payment success"));
    }

}
