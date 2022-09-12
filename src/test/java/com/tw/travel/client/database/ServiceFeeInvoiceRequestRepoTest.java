package com.tw.travel.client.database;


import com.tw.helper.Story;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRequestEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRequestRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
public class ServiceFeeInvoiceRequestRepoTest {

    @Autowired
    private ServiceFeeInvoiceRequestRepo repo;

    @Story("Story2 -> AC1 -> Example1 -> Work step 3")
    @Test
    void should_return_saved_invoice_request_when_save() {
        Instant invoiceRequestCreatedAt = Instant.parse("2022-08-25T15:30:00Z");
        ServiceFeeInvoiceRequestEntity serviceFeeInvoiceRequestEntity = new ServiceFeeInvoiceRequestEntity(1L, "pending", invoiceRequestCreatedAt, invoiceRequestCreatedAt.plusSeconds(24 * 60 * 60), invoiceRequestCreatedAt);

        ServiceFeeInvoiceRequestEntity actualSavedEntity = repo.save(serviceFeeInvoiceRequestEntity);

        Assertions.assertEquals(serviceFeeInvoiceRequestEntity, actualSavedEntity);
        Assertions.assertEquals("2022-08-26T15:30:00Z", actualSavedEntity.getExpiredAt().toString());
    }

    @Story("Story2 -> AC1 -> Example2 -> Work step 3")
    @Test
    void should_return_saved_invoice_request_when_findById() {
        Instant invoiceRequestCreatedAt = Instant.parse("2022-08-24T19:30:00Z");
        ServiceFeeInvoiceRequestEntity expected = new ServiceFeeInvoiceRequestEntity(1L, "pending", invoiceRequestCreatedAt, invoiceRequestCreatedAt.plusSeconds(24 * 60 * 60), invoiceRequestCreatedAt);
        repo.save(expected);

        ServiceFeeInvoiceRequestEntity savedEntity = repo.findById(1L).get();

        Assertions.assertEquals(expected, savedEntity);
        Assertions.assertEquals("2022-08-25T19:30:00Z", savedEntity.getExpiredAt().toString());
    }
}
