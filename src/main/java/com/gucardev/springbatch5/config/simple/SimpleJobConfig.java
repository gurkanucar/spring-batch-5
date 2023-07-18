package com.gucardev.springbatch5.config.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class SimpleJobConfig {

  @Bean
  public Job myJob(
      JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new JobBuilder("myJob", jobRepository)
        .start(firstStep(jobRepository, platformTransactionManager))
        .build();
  }

  public Step firstStep(
      JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("firstStep", jobRepository)
        .tasklet(myTasklet(), platformTransactionManager)
        .build();
  }

  public Tasklet myTasklet() {
    return ((contribution, chunkContext) -> {
      log.info("##### first tasklet step");
      return RepeatStatus.FINISHED;
    });
  }
}
