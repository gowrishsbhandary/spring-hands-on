package com.projectx.spring.hands.on.controller;

import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import com.projectx.spring.hands.on.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Api(value = "", tags = "Adds and retrieves the user")
public class UserController {

  private final UserRepository userRepository;
  private final UserService userService;

  @ApiOperation(value = "Returns all the users in the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @GetMapping(value = "/getAll")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @ApiOperation(value = "Returns the user for the given user name")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @GetMapping(value = "/{userName}")
  public Optional<User> getUserByName(@PathVariable String userName) {
    return userRepository.findByUserName(userName);
  }

  @ApiOperation(value = "Add the user to the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @PostMapping(value = "/register")
  public Optional<User> registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @ApiOperation(value = "Load users to the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @PostMapping(value = "/load")
  public BatchStatus loadUsers()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
          JobParametersInvalidException, JobRestartException {
    return userService.executeJob();
  }
}
