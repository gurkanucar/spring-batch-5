package com.gucardev.springbatch5.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class MyJobListener implements JobExecutionListener {

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("########### JOB IS STARTING");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    log.info("########### JOB FINISHED");
  }
}
