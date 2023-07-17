package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.Address;
import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import com.gucardev.springbatch5.service.AddressService;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
@Configuration
@EnableBatchProcessing
public class BatchConfig {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final UserRepository userRepository;
  private final AddressService addressService;

  public BatchConfig(
          JobBuilderFactory jobBuilderFactory,
          StepBuilderFactory stepBuilderFactory,
          UserRepository userRepository,
          AddressService addressService) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.userRepository = userRepository;
    this.addressService = addressService;
  }

  @Bean
  public JobBuilderFactory jobBuilderFactory() {
    return new JobBuilderFactory(stepBuilderFactory.getJobRepository(), transactionManager);
  }
  @Bean
  public Step processUsersStep() {
    return stepBuilderFactory
        .get("processUsersStep")
        .<User, User>chunk(10) // Process users in chunks of 10
        .reader(userItemReader())
        .processor(userItemProcessor())
        .writer(userItemWriter())
        .build();
  }

  @Bean
  public ItemReader<User> userItemReader() {
    // Customize the User reader according to your needs
    return new JpaPagingItemReaderBuilder<User>()
        .name("userItemReader")
        .queryString("SELECT u FROM User u WHERE u.processed = 0")
        .build();
  }

  @Bean
  public ItemProcessor<User, User> userItemProcessor() {
    return user -> {
      // Set the user's addresses using the addressService
      List<Address> addresses = addressService.getAddressByUserId(user.getId());
      user.setAddresses(addresses);
      return user;
    };
  }

  @Bean
  public ItemWriter<User> userItemWriter() {
    // Customize the User writer according to your needs
    return items -> {
      for (User user : items) {
        // Perform any necessary processing or persistence logic
        userRepository.save(user);
      }
    };
  }

  @Bean
  public Job userProcessingJob(Step processUsersStep) {
    return jobBuilderFactory.get("userProcessingJob").start(processUsersStep).build();
  }
}

*/