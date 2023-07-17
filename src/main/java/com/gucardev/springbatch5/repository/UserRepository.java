package com.gucardev.springbatch5.repository;

import com.gucardev.springbatch5.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByProcessed(int processed);

  @Query("SELECT u FROM User u WHERE u.processed = :processed")
  Page<User> findByProcessed(@Param("processed") int processed, Pageable pageable);
}
