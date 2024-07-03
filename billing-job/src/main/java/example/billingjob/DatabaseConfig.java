package example.billingjob;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;

//https://lahuman.github.io/springboot_multi_datasource/

@Configuration
public class DatabaseConfig {
    @Bean
    @Primary
    @BatchDataSource
    @ConfigurationProperties("spring.datasource.main")
    DataSource springBatchDb(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.type(HikariDataSource.class);
        return builder.build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.second")
    DataSource workDb(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.type(HikariDataSource.class);
        return builder.build();
    }

    // Transaction Setting

    @Bean
    @Primary
    JdbcTransactionManager springBatchTxManager() {
        return new JdbcTransactionManager(springBatchDb());
    }

    @Bean
    JdbcTransactionManager workTxManager() {
        return new JdbcTransactionManager(workDb());
    }
}
