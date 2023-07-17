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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = {"com.gucardev.springbatch5.repository.secondary"})
public class SecondaryDataSourceConfig {

  @Bean(name = "secondaryDataSourceProperties")
  @ConfigurationProperties("spring.datasource-secondary")
  public DataSourceProperties secondaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "secondaryDataSource")
  @ConfigurationProperties("spring.datasource-secondary.configuration")
  public DataSource secondaryDataSource(
      @Qualifier("secondaryDataSourceProperties")
          DataSourceProperties secondaryDataSourceProperties) {
    return secondaryDataSourceProperties
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean(name = "secondaryEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
      EntityManagerFactoryBuilder secondaryEntityManagerFactoryBuilder,
      @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {

    Map<String, String> secondaryJpaProperties = new HashMap<>();
    secondaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    secondaryJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

    return secondaryEntityManagerFactoryBuilder
        .dataSource(secondaryDataSource)
        .packages("com.gucardev.springbatch5.model.batch")
        .persistenceUnit("secondaryDataSource")
        .properties(secondaryJpaProperties)
        .build();
  }

  @Bean(name = "secondaryTransactionManager")
  public PlatformTransactionManager secondaryTransactionManager(
      @Qualifier("secondaryEntityManagerFactory")
          EntityManagerFactory secondaryEntityManagerFactory) {

    return new JpaTransactionManager(secondaryEntityManagerFactory);
  }
}
