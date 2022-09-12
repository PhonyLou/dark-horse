package com.tw.travel.client.database;


import com.tw.travel.client.database.payment.ServiceFeePaymentEntity;
import com.tw.travel.client.database.payment.ServiceFeePaymentRepo;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
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

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void beforeEach() {
//        flyway.clean();
//        flyway.migrate();
    }

    /**
     * Story1 AC1 Example1
     */
    @Test
    void should_return_travelId_with_pending_status_when_save_given_no_records_exist() {
        LocalDate paymentRequestDate = LocalDate.parse("2022-09-11");
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending", paymentRequestDate, paymentRequestDate.plusDays(5), paymentRequestDate);
        ServiceFeePaymentEntity actualSavedEntity = repo.save(expectedEntity);
        assertEquals(expectedEntity, actualSavedEntity);
    }

    /**
     * Story1 AC1 Example1
     */
    @Test
    void should_return_success_status_when_updateStatus_given_records_exist() {
        LocalDate paymentRequestDate = LocalDate.parse("2022-09-11");
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending", paymentRequestDate, paymentRequestDate.plusDays(5), paymentRequestDate);
        ServiceFeePaymentEntity savedEntity = repo.save(expectedEntity);
        assertEquals("pending", savedEntity.getStatus());

        Integer successRecord = repo.updateStatus(1L, "success", paymentRequestDate.plusDays(1));
        assertEquals(1, successRecord);

        ServiceFeePaymentEntity actualUpdatedEntity = repo.findById(1L).get();
        assertEquals(new ServiceFeePaymentEntity(1L,
                        "success",
                        paymentRequestDate,
                        paymentRequestDate.plusDays(5),
                        paymentRequestDate.plusDays(1)),
                actualUpdatedEntity);
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

    @Test
    void test_save_timestamp() {
//        LocalDate createDay = LocalDate.parse("2022-09-12");
//        ServiceFeePaymentEntityWithTime inputEntity = new ServiceFeePaymentEntityWithTime(1L,
//                "pending", createDay, createDay.plusDays(5), createDay);
//        ServiceFeePaymentEntityWithTime savedEntity = paymentRepo.save(inputEntity);
//
//        System.out.println(savedEntity);
////        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending");
//        Assertions.assertEquals("pending", savedEntity.getStatus());
//
//        ServiceFeePaymentEntityWithTime successEntity = new ServiceFeePaymentEntityWithTime(1L,
//                "success", null, null, createDay.plusDays(1));
//        Integer result = paymentRepo.updateStatus(successEntity.getTravelContractId(), "success", successEntity.getLastUpdate());
//        System.out.println("updated to ===> " + result);
//
//        ServiceFeePaymentEntityWithTime serviceFeePaymentEntityWithTime = paymentRepo.findById(1L).get();
//        System.out.println("serviceFeePaymentEntityWithTime => " + serviceFeePaymentEntityWithTime);
    }
}
