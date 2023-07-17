package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {
  @Autowired private DataSource dataSource;

  @Bean
  Job createJob(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      UserRepository userRepository) {
    return new JobBuilder("job", jobRepository)
        .flow(createStep(jobRepository, transactionManager, userRepository))
        .end()
        .build();
  }

  @Bean
  Step createStep(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      UserRepository userRepository) {
    return new StepBuilder("step", jobRepository)
        .<User, User>chunk(5, transactionManager)
        .allowStartIfComplete(true)
        .reader(reader())
        .processor(new CustomProcessor())
        .writer(new CustomWriter(userRepository))
        .build();
  }

  @Bean
  public JdbcPagingItemReader<User> reader() {
    JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
    reader.setDataSource(this.dataSource);
    reader.setFetchSize(1000);
    reader.setRowMapper(new UserRowMapper());

    Map<String, Order> sortKeys = new HashMap<>();
    sortKeys.put("id", Order.ASCENDING);

    H2PagingQueryProvider queryProvider = new H2PagingQueryProvider();
    queryProvider.setSelectClause("select u.id, u.name, u.username, u.processed");
    queryProvider.setFromClause("from USERS u");
   // queryProvider.setWhereClause("u.processed = 0");
    queryProvider.setSortKeys(sortKeys);

    reader.setQueryProvider(queryProvider);

    return reader;
  }
}
