package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import java.util.Iterator;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

// public class CustomReader implements ItemReader<String> {
//
//    private String[] tokens = { "Java", "Spring", "SpringBoot", "Hibernate", "SpringBootBatch",
// "Advanced Java" };
//
//    private int index = 0;
//
//    @Override
//    public String read() throws Exception {
//        if (index >= tokens.length) {
//            return null;
//        }
//
//        String data = index + " " + tokens[index];
//        index++;
//
//        System.out.println("Reading data - " + data);
//        return data;
//    }
//
// }

// @Component
// public class CustomReader implements ItemReader<User> {
//  private final UserRepository userRepository;
//  private int index = 0;
//  private User[] users = {
//    new User("grkn", "@grkn", 0),
//    new User("ahmet", "@ahmet", 0),
//    new User("mehmet", "@mehmet", 1),
//    new User("can", "@can", 0)
//  };
//
//  private List<User> userList;
//
//  public CustomReader(UserRepository userRepository) {
//    this.userRepository = userRepository;
//    this.userList = userRepository.findAll();
//  }
//
//  @Override
//  public User read() throws Exception {
//    if (index >= userList.size()) {
//      return null;
//    }
//
//    User data = userList.get(index);
//    index++;
//
//    System.out.println("Reading data - " + data);
//    return data;
//  }
// }

public class CustomReader implements ItemReader<User> {
  private final UserRepository userRepository;
  private int pageIndex = 0;
  private int pageSize = 5; // size of each chunk
  private Iterator<User> currentPageIterator;

  public CustomReader(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.currentPageIterator = fetchNextPageIterator();
  }

  @Override
  public User read() throws Exception {
    if (currentPageIterator == null) {
      // No more data to read, the end of the input is reached
      return null;
    }

    if (!currentPageIterator.hasNext()) {
      // Reached the end of the current page, fetch the next page
      currentPageIterator = fetchNextPageIterator();
      if (currentPageIterator == null) {
        // No more data to read, the end of the input is reached
        return null;
      }
    }

    User data = currentPageIterator.next();
    System.out.println("Reading data - " + data);
    return data;
  }

  private Iterator<User> fetchNextPageIterator() {
    Page<User> nextPage = userRepository.findAll(PageRequest.of(pageIndex, pageSize));
    if (nextPage.hasContent()) {
      pageIndex++;
      return nextPage.iterator();
    } else {
      // No more data to read, the end of the input is reached
      return null;
    }
  }
}
