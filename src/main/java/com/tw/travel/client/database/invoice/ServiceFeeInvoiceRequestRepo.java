package com.tw.travel.client.database.invoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceFeeInvoiceRequestRepo extends CrudRepository<ServiceFeeInvoiceRequestEntity, Long> {
}
