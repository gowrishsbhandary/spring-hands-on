package com.projectx.spring.hands.on.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("prod")
@Configuration
@Slf4j
public class DevProfileConfig {

  @PostConstruct
  public void getProfileDetails() {
    log.info("==================== Loading PROD profile ====================");
  }
}
