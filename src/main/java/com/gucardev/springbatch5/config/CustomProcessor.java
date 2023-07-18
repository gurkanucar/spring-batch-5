package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.Address;
import com.gucardev.springbatch5.model.User;
import java.util.Collections;
import java.util.List;
import org.springframework.batch.item.ItemProcessor;

public class CustomProcessor implements ItemProcessor<User, User> {

  @Override
  public User process(User user) throws Exception {
    System.out.println("Processing data - " + user.getUsername());
    user.setUsername(user.getUsername() + "_UPD");
    user.setProcessed(1);
    var addresses = getAddressForUser(user);
    user.setAddresses(addresses);
    return user;
  }

  private List<Address> getAddressForUser(User user) {
    Address address = new Address();
    address.setStreet("123 Main St");
    address.setCity("Springfield");
    address.setCountry("USA");
    address.setUser(user);
    return Collections.singletonList(address);
  }
}
