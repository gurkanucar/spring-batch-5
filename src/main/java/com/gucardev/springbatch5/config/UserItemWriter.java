package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import com.gucardev.springbatch5.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserItemWriter implements ItemWriter<User> {

    private final UserRepository userRepository;

    public UserItemWriter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void write(List<? extends User> users) throws Exception {
        userRepository.saveAll(users); // Save the updated users
    }
}
