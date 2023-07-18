package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {

  @Bean
  Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new JobBuilder("job", jobRepository)
        .listener(new MyJobListener())
        .flow(createStep(jobRepository, transactionManager))
        .end()
        .build();
  }

  @Bean
  Step createStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("step", jobRepository)
        .<User, User>chunk(3, transactionManager)
        .listener(new MyStepListener())
        .reader(new UserReader())
        .processor(new UserProcessor())
        .writer(new UserWriter())
        .build();
  }
}
