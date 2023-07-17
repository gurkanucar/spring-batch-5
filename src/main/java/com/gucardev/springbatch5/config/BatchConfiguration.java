package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import com.gucardev.springbatch5.service.AddressService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
  public Step step(
      ItemReader<User> itemReader,
      ItemProcessor<User, User> itemProcessor,
      ItemWriter<User> itemWriter,
      StepBuilderFactory stepBuilderFactory) {
    return stepBuilderFactory
        .get("step")
        .<User, User>chunk(10)
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .build();
  }

  @Bean
  public ItemReader<User> itemReader() {
    return new UserItemReader(userRepository);
  }

  @Bean
  public ItemProcessor<User, User> itemProcessor() {
    return new UserItemProcessor(addressService);
  }

  @Bean
  public ItemWriter<User> itemWriter() {
    return new UserItemWriter(userRepository);
  }
}
