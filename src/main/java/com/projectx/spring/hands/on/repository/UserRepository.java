package com.projectx.spring.hands.on.repository;

import com.projectx.springjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByName(String name);
}
