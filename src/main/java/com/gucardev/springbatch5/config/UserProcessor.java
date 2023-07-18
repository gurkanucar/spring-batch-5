package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class UserProcessor implements ItemProcessor<User, User> {

  @Override
  public User process(User user) throws Exception {
    log.info("Processing data - {}", user.getUsername());
    user.setUsername(user.getUsername() + "_UPD");
    user.setProcessed(1);
    return user;
  }
}
