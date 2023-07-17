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

    //        List<Address> addresses = new ArrayList<>();
    //        do {
    //            Long addressId = rs.getLong("address_id");
    //            if (addressId != 0) {
    //                String street = rs.getString("street");
    //                String city = rs.getString("city");
    //                String country = rs.getString("country");
    //
    //                Address address = new Address();
    //                address.setId(addressId);
    //                address.setStreet(street);
    //                address.setCity(city);
    //                address.setCountry(country);
    //
    //                addresses.add(address);
    //            }
    //        } while (rs.next() && userId.equals(rs.getLong("id")));

    User user = new User(userId, name, username, processed, null);
    return user;
  }
}
