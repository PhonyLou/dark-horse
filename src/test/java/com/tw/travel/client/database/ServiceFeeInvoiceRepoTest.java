package com.tw.travel.client.database;


import com.tw.helper.Story;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceEntity;
import com.tw.travel.client.database.invoice.ServiceFeeInvoiceRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceFeeInvoiceRepoTest {

    @Autowired
    private ServiceFeeInvoiceRepo repo;

    @Story("Story2 -> AC3 -> Example2 -> Work step 3")
    @Test
    void should_return_saved_invoice_when_save() {
        ServiceFeeInvoiceEntity expectedInvoiceEntity = new ServiceFeeInvoiceEntity(1L, BigDecimal.valueOf(1000L), "sample-content", "3-12345");
        ServiceFeeInvoiceEntity actualSavedEntity = repo.save(expectedInvoiceEntity);
        Assertions.assertEquals(expectedInvoiceEntity, actualSavedEntity);
    }
}
