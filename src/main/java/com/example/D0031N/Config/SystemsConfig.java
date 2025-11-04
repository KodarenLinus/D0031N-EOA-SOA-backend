package com.example.D0031N.Config;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SystemsConfig {

    // ----- Epok (PRIMARY / default) -----
    @Bean
    @ConfigurationProperties("spring.datasource.epok")
    public DataSourceProperties epokProps() { return new DataSourceProperties(); }

    @Bean("epokDs")
    @Primary
    public DataSource epokDs(@Qualifier("epokProps") DataSourceProperties p) {
        return p.initializeDataSourceBuilder()
                .type(HikariDataSource.class)     // säkerställ Hikari
                .build();
    }

    @Bean("epokJdbi")
    @Primary
    public Jdbi epokJdbi(@Qualifier("epokDs") DataSource ds) {
        return Jdbi.create(ds).installPlugin(new SqlObjectPlugin());
    }

    @Bean("epokTx")
    @Primary
    public DataSourceTransactionManager epokTx(@Qualifier("epokDs") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    // ----- StudentITS -----
    @Bean
    @ConfigurationProperties("spring.datasource.studentits")
    public DataSourceProperties studentProps() { return new DataSourceProperties(); }

    @Bean("studentDs")
    public DataSource studentDs(@Qualifier("studentProps") DataSourceProperties p) {
        return p.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("studentJdbi")
    public Jdbi studentJdbi(@Qualifier("studentDs") DataSource ds) {
        return Jdbi.create(ds).installPlugin(new SqlObjectPlugin());
    }

    @Bean("studentTx")
    public DataSourceTransactionManager studentTx(@Qualifier("studentDs") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    // ----- Ladok -----
    @Bean
    @ConfigurationProperties("spring.datasource.ladok")
    public DataSourceProperties ladokProps() { return new DataSourceProperties(); }

    @Bean("ladokDs")
    public DataSource ladokDs(@Qualifier("ladokProps") DataSourceProperties p) {
        return p.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("ladokJdbi")
    public Jdbi ladokJdbi(@Qualifier("ladokDs") DataSource ds) {
        return Jdbi.create(ds).installPlugin(new SqlObjectPlugin());
    }

    @Bean("ladokTx")
    public DataSourceTransactionManager ladokTx(@Qualifier("ladokDs") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
