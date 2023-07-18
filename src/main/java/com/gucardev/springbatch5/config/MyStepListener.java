package com.gucardev.springbatch5.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class MyStepListener implements StepExecutionListener {
  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info("########### STEP IS STARTING");
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("########### STEP FINISHED");
    return StepExecutionListener.super.afterStep(stepExecution);
  }
}
