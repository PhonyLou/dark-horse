package com.tw.travel.controller.invoice;

import com.tw.travel.service.invoice.ServiceFeeInvoiceModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceFeeInvoiceController {

    private final ServiceFeeInvoiceService service;

    public ServiceFeeInvoiceController(ServiceFeeInvoiceService service) {
        this.service = service;
    }

    @PostMapping("/travel-contracts/{tid}/service-fee-invoices")
    final public ResponseEntity payServiceFee(@PathVariable("tid") Long travelContractId, @RequestBody ServiceFeeInvoiceRequest req) {
        ServiceFeeInvoiceModel serviceFeeInvoiceModel = service.issueServiceFeeInvoice(travelContractId, req.getAmount(), req.getCreatedAt());
        if (serviceFeeInvoiceModel.isSuccess()) {
            return ResponseEntity.ok(new ServiceFeeInvoiceDTO("invoice request accepted"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("invoice request not accepted"));
        }
    }

    @PostMapping("/travel-contracts/{tid}/service-fee-invoices/confirmation")
    final public ResponseEntity storeServiceFeeInvoice(@PathVariable("tid") Long travelContractId, @RequestBody ServiceFeeInvoiceConfirmationRequest req) {
        service.storeServiceFeeInvoice(travelContractId, req.getInvoiceContent(), req.getAmount(), req.getInvoiceNumber(), req.getCreatedAt());
        return ResponseEntity.ok(new ServiceFeeInvoiceDTO("invoice saved"));
    }

}
