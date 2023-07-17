package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.Address;
import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.service.AddressService;
import java.util.List;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserItemProcessor implements ItemProcessor<User, User> {

  private final AddressService addressService;

  public UserItemProcessor(AddressService addressService) {
    this.addressService = addressService;
  }

  @Override
  public User process(User user) throws Exception {
    List<Address> addresses = addressService.getAddressByUserId(user.getId());
    user.setAddresses(addresses); // Set the addresses for the user
    return user;
  }
}
