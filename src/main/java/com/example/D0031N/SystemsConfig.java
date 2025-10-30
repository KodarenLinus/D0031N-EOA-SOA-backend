package com.example.D0031N;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
public class SystemsConfig {

    // ---- Epok ----
    @Bean @ConfigurationProperties("spring.datasource.epok")
    public DataSourceProperties epokProps() { return new DataSourceProperties(); }

    @Bean("epokDs")
    public DataSource epokDs(@Qualifier("epokProps") DataSourceProperties p) {
        return p.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("epokJdbi")
    public Jdbi epokJdbi(@Qualifier("epokDs") DataSource ds) {
        return Jdbi.create(ds).installPlugin(new SqlObjectPlugin());
    }

    @Bean(name="epokFlyway", initMethod="migrate")
    public Flyway epokFlyway(@Qualifier("epokDs") DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .locations("classpath:db/migration/epok").load();
    }

    // ---- StudentITS ----
    @Bean @ConfigurationProperties("spring.datasource.studentits")
    public DataSourceProperties studentProps() { return new DataSourceProperties(); }

    @Bean("studentDs")
    public DataSource studentDs(@Qualifier("studentProps") DataSourceProperties p) {
        return p.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("studentJdbi")
    public Jdbi studentJdbi(@Qualifier("studentDs") DataSource ds) {
        return Jdbi.create(ds).installPlugin(new SqlObjectPlugin());
    }

    @Bean(name="studentFlyway", initMethod="migrate")
    public Flyway studentFlyway(@Qualifier("studentDs") DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .locations("classpath:db/migration/studentits").load();
    }

    // ---- Ladok ----
    @Bean @ConfigurationProperties("spring.datasource.ladok")
    public DataSourceProperties ladokProps() { return new DataSourceProperties(); }

    @Bean("ladokDs")
    public DataSource ladokDs(@Qualifier("ladokProps") DataSourceProperties p) {
        return p.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("ladokJdbi")
    public Jdbi ladokJdbi(@Qualifier("ladokDs") DataSource ds) {
        return Jdbi.create(ds).installPlugin(new SqlObjectPlugin());
    }

    @Bean(name="ladokFlyway", initMethod="migrate")
    public Flyway ladokFlyway(@Qualifier("ladokDs") DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .locations("classpath:db/migration/ladok").load();
    }
}