package com.tw.darkhorse.outbound.database;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class DemoRepositoryTest {

    @Autowired
    private DemoRepository demoRepository;

    @Test
    void should_return_entity_when_query_by_id_given_data_exists() {
        Optional<DemoEntity> entity = demoRepository.findById(1L);
        Assertions.assertEquals(new DemoEntity(1L, "myName"), entity.get());
    }
}
