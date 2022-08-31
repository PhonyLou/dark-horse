package com.tw.darkhorse.outbound.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends CrudRepository<DemoEntity, Long> {
}
