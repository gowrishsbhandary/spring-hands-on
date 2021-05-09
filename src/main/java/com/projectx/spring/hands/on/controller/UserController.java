package com.projectx.spring.hands.on.controller;

import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

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
    userRepository.save(user);
    return userRepository.findByName(user.getName());
  }
}
