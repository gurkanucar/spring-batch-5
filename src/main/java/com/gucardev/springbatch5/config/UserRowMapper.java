package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    Long userId = rs.getLong("id");
    String name = rs.getString("name");
    String username = rs.getString("username");
    int processed = rs.getInt("processed");

    User user = new User(userId, name, username, processed, null);
    return user;
  }
}
