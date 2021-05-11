package com.projectx.spring.hands.on.batch;

import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserWriter implements ItemWriter<User> {

  private final UserRepository userRepository;

  @Override
  public void write(List<? extends User> users) throws Exception {
    userRepository.saveAll(users);
    log.info("Users data is saved {}", users);
  }
}
