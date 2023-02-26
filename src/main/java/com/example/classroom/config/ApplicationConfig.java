package com.example.classroom.config;

//import org.flywaydb.core.Flyway;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class ApplicationConfig {
    @Autowired
    private DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("jdbc:postgresql://localhost:5432/postgres")
                .baselineOnMigrate(true)
                .load();
    }
}
