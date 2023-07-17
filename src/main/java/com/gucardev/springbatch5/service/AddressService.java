package com.gucardev.springbatch5.service;

import com.gucardev.springbatch5.model.Address;
import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.AddressRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;
  private final UserService userService;

  public List<Address> getAll() {
    return addressRepository.findAll();
  }

  public List<Address> getAddressByUserId(Long userId) {
    return Collections.singletonList(new Address());
  }

  public Address create(Address addressRequest) {
    User user = userService.findUserById(addressRequest.getUser().getId());
    addressRequest.setUser(user);
    return addressRepository.save(addressRequest);
  }

  public Address update(Address addressRequest) {
    Address address = findAddressById(addressRequest.getId());
    BeanUtils.copyProperties(addressRequest, address, "id", "user");
    return addressRepository.save(address);
  }

  public void delete(Long id) {
    if (addressRepository.existsById(id)) {
      addressRepository.deleteById(id);
    } else {
      throw new RuntimeException("Address not found with id: " + id);
    }
  }

  private Address findAddressById(Long id) {
    return addressRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(("Address not found with id: " + id)));
  }
}
