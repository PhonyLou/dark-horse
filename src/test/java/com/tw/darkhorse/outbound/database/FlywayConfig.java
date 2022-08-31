package com.tw.darkhorse.outbound.database;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class FlywayConfig {
//    @Bean
//    public Flyway flyway(DataSource theDataSource) {
//        Flyway flyway = new Flyway(Configuration);
//        flyway.setDataSource(theDataSource);
//        flyway.setLocations("classpath:db/migration");
//        flyway.clean();
//        flyway.migrate();
//
//        return flyway;
//    }
}
