package com.tw.travel.controller.invoice;

import com.tw.travel.service.invoice.ServiceFeeInvoiceModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;

@RestController
public class ServiceFeeInvoiceController {

    private final ServiceFeeInvoiceService service;

    public ServiceFeeInvoiceController(ServiceFeeInvoiceService service) {
        this.service = service;
    }

    @PostMapping("/travel-contracts/{tid}/service-fee-invoices")
    final public ResponseEntity payServiceFee(@PathVariable("tid") Long travelContractId, @Valid @RequestBody ServiceFeeInvoiceRequest req) {
        // 验证合同ID
        if (travelContractId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("Invalid contract ID"));
        }

        // 验证时间
        if (req.getCreatedAt().isAfter(Instant.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("CreatedAt cannot be in the future"));
        }
        if (req.getCreatedAt().isBefore(Instant.parse("2020-01-01T00:00:00Z"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("Date is too old"));
        }

        try {
            ServiceFeeInvoiceModel serviceFeeInvoiceModel = service.issueServiceFeeInvoice(travelContractId, req.getAmount(), req.getCreatedAt());
            if (serviceFeeInvoiceModel.isSuccess()) {
                return ResponseEntity.ok(new ServiceFeeInvoiceDTO("invoice request accepted"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("invoice request not accepted"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO(e.getMessage()));
        }
    }

    @PostMapping("/travel-contracts/{tid}/service-fee-invoices/confirmation")
    final public ResponseEntity storeServiceFeeInvoice(@PathVariable("tid") Long travelContractId, @Valid @RequestBody ServiceFeeInvoiceConfirmationRequest req) {
        // 验证合同ID
        if (travelContractId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("Invalid contract ID"));
        }

        // 验证发票号格式
        if (!req.getInvoiceNumber().matches("\\d+-\\d+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("Invalid invoice number format"));
        }

        // 验证时间
        if (req.getCreatedAt().isAfter(Instant.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO("CreatedAt cannot be in the future"));
        }

        try {
            service.storeServiceFeeInvoice(travelContractId, req.getInvoiceContent(), req.getAmount(), req.getInvoiceNumber(), req.getCreatedAt());
            return ResponseEntity.ok(new ServiceFeeInvoiceDTO("invoice saved"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceFeeInvoiceDTO(e.getMessage()));
        }
    }
}
