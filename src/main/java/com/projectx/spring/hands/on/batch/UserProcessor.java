package com.projectx.spring.hands.on.batch;

import com.projectx.spring.hands.on.model.Role;
import com.projectx.spring.hands.on.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class UserProcessor implements ItemProcessor<User, User> {

  private static final Map<String, String> DESIGNATION = new HashMap<>();

  public UserProcessor() {
    DESIGNATION.put("001", "Engineer");
    DESIGNATION.put("002", "Manager");
    DESIGNATION.put("003", "HR");
  }

  @Override
  public User process(User user) throws Exception {

    String designationCode = user.getDesignation();
    String designation = DESIGNATION.get(designationCode);
    log.info("Converted from {} to {}", designationCode, designation);
    user.setDesignation(designation);
    user.setCreateTime(LocalDateTime.now());
    Role role = new Role();
    role.setRole("USER");
    user.setRoles(Stream.of(role).collect(Collectors.toCollection(HashSet::new)));

    return user;
  }
}
