package com.gucardev.springbatch5.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//  @Bean
//  public DataSource primaryDataSource() {
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    dataSource.setDriverClassName("org.h2.Driver");
//    dataSource.setUrl("jdbc:h2:mem:primarydb;DB_CLOSE_DELAY=-1");
//    dataSource.setUsername("sa");
//    dataSource.setPassword("");
//    return dataSource;
//  }

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager",
    basePackages = {"com.gucardev.springbatch5.repository.primary"})
public class PrimaryDataSourceConfig {

  @Primary
  @Bean(name = "primaryDataSourceProperties")
  @ConfigurationProperties("spring.datasource-primary")
  public DataSourceProperties primaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "primaryDataSource")
  @ConfigurationProperties("spring.datasource-primary.configuration")
  public DataSource primaryDataSource(
      @Qualifier("primaryDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
    return primaryDataSourceProperties
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Primary
  @Bean(name = "primaryEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
      EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder,
      @Qualifier("primaryDataSource") DataSource primaryDataSource) {

    Map<String, String> primaryJpaProperties = new HashMap<>();
    // update dialect
    primaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    primaryJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

    return primaryEntityManagerFactoryBuilder
        .dataSource(primaryDataSource)
        .packages("com.gucardev.springbatch5.model.entity")
        .persistenceUnit("primaryDataSource")
        .properties(primaryJpaProperties)
        .build();
  }

  @Primary
  @Bean(name = "primaryTransactionManager")
  public PlatformTransactionManager primaryTransactionManager(
      @Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {

    return new JpaTransactionManager(primaryEntityManagerFactory);
  }
}
