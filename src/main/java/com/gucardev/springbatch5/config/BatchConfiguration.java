package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.Address;
import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import com.gucardev.springbatch5.service.AddressService;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  private final UserRepository userRepository;
  private final AddressService addressService;

  public BatchConfiguration(UserRepository userRepository, AddressService addressService) {
    this.userRepository = userRepository;
    this.addressService = addressService;
  }

  @Bean
  public Job job(JobRepository jobRepository, Step step) {
    return new JobBuilder("job").repository(jobRepository).start(step).build();
  }

  @Bean
  public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("step")
        .repository(jobRepository)
        .transactionManager(transactionManager)
        .tasklet(
            (contribution, chunkContext) -> {
              List<User> users = userRepository.findByProcessed(0); // Fetch users with processed=0
              for (User user : users) {
                List<Address> addresses = addressService.getAddressByUserId(user.getId());
                user.setAddresses(addresses); // Set the addresses for the user
              }
              userRepository.saveAll(users); // Save the updated users
              return RepeatStatus.FINISHED;
            })
        .build();
  }
}
