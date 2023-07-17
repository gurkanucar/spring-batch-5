package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;

import java.util.*;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

public class CustomReader implements ItemReader<List<User>> {
  private UserRepository userRepository;
  private int pageSize;
  private int currentPage = 0;

  // Constructor to inject dependencies
  public CustomReader(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.pageSize = 10;
  }

  @Override
  public List<User> read() {
    // Calculate the offset based on the current page and page size
    int offset = currentPage * pageSize;

    // Fetch a page of users from the repository
    List<User> users =
        userRepository.findByProcessed(1, PageRequest.of(offset, pageSize)).getContent();

    currentPage++;

    return users;
  }
}
