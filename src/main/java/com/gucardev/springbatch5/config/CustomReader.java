package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;

import java.util.*;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CustomReader implements ItemReader<User> {

  private final JdbcTemplate jdbcTemplate;

  private int currentRowIndex;
  private List<User> users;

  public CustomReader(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.currentRowIndex = 0;
  }

  @Override
  public User read() {
    if (users == null) {
      // Fetch the users from the database and initialize the list
      users = fetchUsersFromDatabase();
    }

    if (currentRowIndex < users.size()) {
      // Return the next user in the list
      return users.get(currentRowIndex++);
    } else {
      // All users have been read
      return null;
    }
  }

  private List<User> fetchUsersFromDatabase() {
    String sql = "SELECT id, name, username, processed FROM USERS WHERE processed = 0 ORDER BY id ASC LIMIT 1000";
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
  }
}
