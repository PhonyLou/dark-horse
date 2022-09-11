package com.tw.travel.client.database;


import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void should_return_travelId_with_pending_status_when_save_given_no_records_exist() {
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending");
        ServiceFeePaymentEntity actualSavedEntity = repo.save(expectedEntity);
        Assertions.assertEquals(expectedEntity, actualSavedEntity);
    }

    @Test
    void should_return_success_status_when_update_status_given_records_exist() {
        ServiceFeePaymentEntity expectedEntity = new ServiceFeePaymentEntity(1L, "pending");
        ServiceFeePaymentEntity savedEntity = repo.save(expectedEntity);
        Assertions.assertEquals("pending", savedEntity.getStatus());

        ServiceFeePaymentEntity expectedUpdateEntity = new ServiceFeePaymentEntity(1L, "success");
        ServiceFeePaymentEntity actualUpdatedEntity = repo.save(expectedUpdateEntity);
        Assertions.assertEquals(expectedUpdateEntity, actualUpdatedEntity);
    }
}
