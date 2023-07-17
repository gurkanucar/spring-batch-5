package com.gucardev.springbatch5.repository;

import com.gucardev.springbatch5.model.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByProcessed(int processed);
}
