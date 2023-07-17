package com.gucardev.springbatch5;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.service.UserService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatch5Application implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringBatch5Application.class, args);
  }

  @Autowired private UserService userService;


  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 30; i++) {
      userService.create(new User("name" + i, "@username" + i, Math.random() > 0.5 ? 0 : 1));
    }

  }
}
