package com.tw.travel.client.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceFeePaymentRepo extends CrudRepository<ServiceFeePaymentEntity, Long> {
}
