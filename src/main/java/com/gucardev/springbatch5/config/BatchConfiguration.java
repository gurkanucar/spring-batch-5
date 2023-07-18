package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Bean
  Job createJob(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      JdbcPagingItemReader<User> reader,
      UserRepository userRepository) {
    return new JobBuilder("job", jobRepository)
        .flow(createStep(jobRepository, transactionManager, reader, userRepository))
        .end()
        .build();
  }

  @Bean
  Step createStep(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      JdbcPagingItemReader<User> reader,
      UserRepository userRepository) {
    return new StepBuilder("step", jobRepository)
        .<User, User>chunk(5, transactionManager)
        .allowStartIfComplete(true)
        .reader(reader)
        .processor(new CustomProcessor())
        .writer(new CustomWriter(userRepository))
        .build();
  }
}
