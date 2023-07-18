package com.gucardev.springbatch5.config.comprehensive;

import com.gucardev.springbatch5.model.User;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class UserWriter implements ItemWriter<User> {

  @Override
  public void write(Chunk<? extends User> chunk) throws Exception {
    if (chunk.getItems().size() == 0) return;
    log.info(
        "Completed writing data - {}",
        Arrays.toString(chunk.getItems().stream().map(User::getUsername).toArray()));
  }
}
