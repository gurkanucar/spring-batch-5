package com.gucardev.springbatch5.service;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User create(User userRequest) {
    return userRepository.save(userRequest);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User update(User userRequest) {
    User user = findUserById(userRequest.getId());
    BeanUtils.copyProperties(userRequest, user, "id", "username");
    return userRepository.save(user);
  }

  public void delete(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new RuntimeException("User not found with id: " + id);
    }
  }

  public User findUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }
}
