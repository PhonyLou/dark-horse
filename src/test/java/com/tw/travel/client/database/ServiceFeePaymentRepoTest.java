package com.tw.travel.client.database;


import com.tw.helper.Story;
import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceFeePaymentRepoTest {

    @Autowired
    private ServiceFeePaymentRepo repo;

    @BeforeEach
    void beforeEach() {
    }

    @Story("Story1 -> AC1 -> Example1 -> Work step 3")
    @Test
    void should_return_travelId_with_pending_status_when_save_given_no_records_exist() {
        LocalDate paymentRequestDate = LocalDate.parse("2022-09-12");
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending", paymentRequestDate, paymentRequestDate.plusDays(5), paymentRequestDate);
        ServiceFeePaymentEntity actualSavedEntity = repo.save(expectedEntity);
        assertEquals(expectedEntity, actualSavedEntity);
    }

    @Story("Story1 -> AC1 -> Example1 -> Work step 3")
    @Test
    void should_return_success_status_when_updateStatus_given_records_exist() {
        LocalDate paymentRequestDate = LocalDate.parse("2022-09-12");
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending", paymentRequestDate, paymentRequestDate.plusDays(5), paymentRequestDate);
        ServiceFeePaymentEntity savedEntity = repo.save(expectedEntity);
        assertEquals("pending", savedEntity.getStatus());

        Integer successRecord = repo.updateStatus(1L, "success", paymentRequestDate);
        assertEquals(1, successRecord);

        ServiceFeePaymentEntity actualUpdatedEntity = repo.findById(1L).get();
        assertEquals(new ServiceFeePaymentEntity(1L,
                        "success",
                        paymentRequestDate,
                        paymentRequestDate.plusDays(5),
                        paymentRequestDate),
                actualUpdatedEntity);
    }

    @Story("Story1 -> AC1 -> Example2 -> Work step 3")
    @Test
    void should_return_entity_when_findById_given_records_exist() {
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending", LocalDate.parse("2022-09-12"), LocalDate.parse("2022-09-12").plusDays(5), LocalDate.parse("2022-09-12"));
        repo.save(expectedEntity);

        ServiceFeePaymentEntity dbEntity = repo.findById(1L).get();

        assertEquals(expectedEntity, dbEntity);
    }

    @Test
    void should_return_entity_with_failed_when_update_status_given_payment_failed() {
        LocalDate paymentRequestDate = LocalDate.parse("2022-09-11");
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending", paymentRequestDate, paymentRequestDate.plusDays(5), paymentRequestDate);
        ServiceFeePaymentEntity savedEntity = repo.save(expectedEntity);
        assertEquals("pending", savedEntity.getStatus());

        ServiceFeePaymentEntity expectedUpdateEntity = new ServiceFeePaymentEntity(1L, "failed", paymentRequestDate, paymentRequestDate.plusDays(5), paymentRequestDate);
        repo.save(expectedUpdateEntity);

        assertEquals("failed", repo.findById(1L).get().getStatus());
    }

}
