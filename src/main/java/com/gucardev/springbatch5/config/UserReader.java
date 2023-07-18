package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class UserReader implements ItemReader<User> {
  private int index = 0;
  private List<User> userList =
      List.of(
          new User("grkn", "@grkn", 0),
          new User("ahmet", "@ahmet", 0),
          new User("mehmet", "@mehmet", 1),
          new User("yilmaz", "@yilmaz", 1),
          new User("sezai", "@sezai", 0),
          new User("kartal", "@kartal", 0),
          new User("ali", "@ali", 1),
          new User("metin", "@metin", 0),
          new User("eda", "@eda", 1),
          new User("ayse", "@ayse", 1),
          new User("fatma", "@fatma", 1),
          new User("hayriye", "@hayriye", 0),
          new User("husnu", "@husnu", 1),
          new User("mesut", "@mesut", 0),
          new User("osman", "@osman", 1),
          new User("erdal", "@erdal", 1),
          new User("erdal", "@erdal", 0),
          new User("polat", "@polat", 0),
          new User("memati", "@memati", 1),
          new User("abdulhey", "@abdulhey", 0),
          new User("karahanli", "@karahanli", 1),
          new User("ziya", "@ziya", 1),
          new User("can", "@can", 0));

  @Override
  public User read() throws Exception {
    if (index >= userList.size()) {
      return null;
    }

    User data = userList.get(index);
    index++;

    log.info("Reading data - {}", data.getUsername());
    return data;
  }
}
