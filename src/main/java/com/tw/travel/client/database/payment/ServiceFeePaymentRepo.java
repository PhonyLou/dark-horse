package com.tw.travel.client.database.payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceFeePaymentRepo extends CrudRepository<ServiceFeePaymentEntity, Long> {
}
