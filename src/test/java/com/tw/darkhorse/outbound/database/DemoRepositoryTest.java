package com.tw.darkhorse.outbound.database;


import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class DemoRepositoryTest {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private Flyway flyway;

    @Test
    @DisplayName("should return 1 when save entity")
    void a1() {
        Configuration configuration = flyway.getConfiguration();
        flyway.clean();
        flyway.migrate();

        var inputEntity = new DemoEntity(1L, "test name");
        var savedEntity = demoRepository.save(inputEntity);
        System.out.println(savedEntity);
        Assertions.assertEquals(inputEntity, savedEntity);
    }

    @Test
    void should_return_entity_when_query_by_id_given_data_exists() {
        Optional<DemoEntity> entity = demoRepository.findById(1L);
        Assertions.assertEquals(new DemoEntity(1L, "myName"), entity.get());
    }
}
