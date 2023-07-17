package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class UserItemReader implements ItemReader<User> {

  private final UserRepository userRepository;
  private List<User> users;
  private int currentIndex = 0;

  public UserItemReader(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User read() throws Exception {
    if (users == null) {
      users = userRepository.findByProcessed(0); // Fetch users with processed=0
    }

    if (currentIndex < users.size()) {
      return users.get(currentIndex++);
    }

    return null;
  }
}
