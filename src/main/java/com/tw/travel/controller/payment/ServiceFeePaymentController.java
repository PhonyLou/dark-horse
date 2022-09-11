package com.tw.travel.controller.payment;

import com.tw.travel.exception.InsufficientFundException;
import com.tw.travel.service.payment.ServiceFeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@RestController
public class ServiceFeePaymentController {

    private final ServiceFeeService serviceFeeService;

    public ServiceFeePaymentController(final ServiceFeeService serviceFeeService) {
        this.serviceFeeService = serviceFeeService;
    }

    @PostMapping("/travel-contracts/{tid}/service-fee-payments")
    final public ResponseEntity payServiceFee(@PathVariable("tid") Long travelContractId, @RequestBody ServiceFeePaymentRequest req) {
        try {
            serviceFeeService.payServiceFee(travelContractId, req.getAmount());
            return ResponseEntity.ok(new ServiceFeePaymentDTO("payment success"));
        } catch (InsufficientFundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeePaymentDTO("insufficient fund"));
        } catch (HttpServerErrorException.InternalServerError | ResourceAccessException error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceFeePaymentDTO("payment failed, try later"));
        }
    }

}
