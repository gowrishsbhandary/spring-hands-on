package com.projectx.spring.hands.on.controller;

import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import com.projectx.spring.hands.on.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
@RestController
public class UserControllerImpl implements UserController {

  private final UserRepository userRepository;
  private final UserService userService;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> getUserByName(@PathVariable String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public Optional<User> registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @Override
  public BatchStatus loadUsers()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
          JobParametersInvalidException, JobRestartException {
    return userService.executeJob();
  }
}
