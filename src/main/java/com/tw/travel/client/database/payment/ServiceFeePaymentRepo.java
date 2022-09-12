package com.tw.travel.client.database.payment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface ServiceFeePaymentRepo extends CrudRepository<ServiceFeePaymentEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "update ServiceFeePaymentEntity p set p.lastUpdate = :last_update, p.status = :status where p.travelContractId = :id")
    Integer updateStatus(@Param("id") Long id, @Param(value = "status") String status, @Param(value = "last_update") LocalDate lastUpdate);

}
