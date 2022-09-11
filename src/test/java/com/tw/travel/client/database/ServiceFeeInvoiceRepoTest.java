package com.tw.travel.client.database;


import com.tw.travel.client.database.invoice.ServiceFeeInvoiceEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class ServiceFeeInvoiceRepoTest {

    @Autowired
    private ServiceFeeInvoiceRepo repo;

    @Test
    void should_return_saved_invoice_when_save() {
        ServiceFeeInvoiceEntity expectedInvoiceEntity = new ServiceFeeInvoiceEntity(1L, BigDecimal.valueOf(1000L), "sample-content", "3-12345");
        ServiceFeeInvoiceEntity actualSavedEntity = repo.save(expectedInvoiceEntity);
        Assertions.assertEquals(expectedInvoiceEntity, actualSavedEntity);
    }
}
