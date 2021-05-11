package com.projectx.spring.hands.on.controller;

import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Api(value = "", description = "Adds and retrieves the user")
public class UserController {

  private final UserRepository userRepository;
  private final JobLauncher jobLauncher;
  private final Job job;

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

  @ApiOperation(value = "Returns the user for the given name")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @GetMapping(value = "/{name}")
  public User getUserByName(@PathVariable String name) {
    return userRepository.findByName(name);
  }

  @ApiOperation(value = "Add the user to the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @PostMapping(value = "/register")
  public User registerUser(@RequestBody User user) {
    user.setCreateTime(LocalDateTime.now());
    userRepository.save(user);
    return userRepository.findByName(user.getName());
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
    Map<String, JobParameter> params = new HashMap<>();
    params.put("time", new JobParameter((System.currentTimeMillis())));
    JobParameters parameters = new JobParameters(params);
    JobExecution jobExecution = jobLauncher.run(job, parameters);
    while (jobExecution.isRunning()) {
      log.debug("Executing job ....");
    }
    log.info("JobExecution status : {} ", jobExecution.getStatus());
    return jobExecution.getStatus();
  }
}
