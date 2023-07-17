package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class CustomWriter implements ItemWriter<User> {

  private final UserRepository userRepository;

  public CustomWriter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void write(Chunk<? extends User> chunk) throws Exception {
    if (chunk.getItems().size() == 0) return;
    userRepository.saveAll(chunk.getItems());
    System.out.println("Completed writing data.");
  }
}
