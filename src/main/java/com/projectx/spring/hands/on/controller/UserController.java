package com.projectx.spring.hands.on.controller;

import com.projectx.springjpa.model.User;
import com.projectx.springjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @GetMapping(value = "/getAll")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping(value = "/{name}")
  public User getUserByName(@PathVariable String name) {
    return userRepository.findByName(name);
  }

  @PostMapping(value = "/register")
  public User registerUser(@RequestBody User user) {
    userRepository.save(user);
    return userRepository.findByName(user.getName());
  }
}
