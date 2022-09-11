package com.tw.travel.controller.invoice;

import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
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
        service.issueServiceFeeInvoice(travelContractId, req.getAmount());
        return ResponseEntity.ok(new ServiceFeeInvoiceDTO("invoice request accepted"));
    }

}
