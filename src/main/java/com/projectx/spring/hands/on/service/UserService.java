package com.projectx.spring.hands.on.service;

import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JobLauncher jobLauncher;
  private final Job job;

  public Optional<User> registerUser(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setCreateTime(LocalDateTime.now());
    userRepository.save(user);
    return userRepository.findByUserName(user.getUserName());
  }

  public BatchStatus executeJob()
      throws JobExecutionAlreadyRunningException, JobRestartException,
          JobInstanceAlreadyCompleteException, JobParametersInvalidException {
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
