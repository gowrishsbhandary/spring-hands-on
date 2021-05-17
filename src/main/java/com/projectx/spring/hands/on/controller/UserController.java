package com.projectx.spring.hands.on.controller;

import com.projectx.spring.hands.on.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@Api(value = "hello", tags = "Adds and retrieves the user")
public interface UserController {

  @ApiOperation(value = "Returns all the users in the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @GetMapping(value = "/getAll")
  List<User> getAllUsers();

  @ApiOperation(value = "Returns the user for the given user name")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @GetMapping(value = "/{userName}")
  Optional<User> getUserByName(@PathVariable String userName);

  @ApiOperation(value = "Add the user to the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @PostMapping(value = "/register")
  Optional<User> registerUser(@RequestBody User user);

  @ApiOperation(value = "Load users to the system")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "successful"),
        @ApiResponse(code = 500, message = "Internal error"),
        @ApiResponse(code = 404, message = "Not found")
      })
  @PostMapping(value = "/load")
  BatchStatus loadUsers()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
          JobParametersInvalidException, JobRestartException;
}
