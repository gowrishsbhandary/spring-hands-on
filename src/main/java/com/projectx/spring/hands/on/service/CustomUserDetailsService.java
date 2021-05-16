package com.projectx.spring.hands.on.service;

import com.projectx.spring.hands.on.model.CustomUserDetails;
import com.projectx.spring.hands.on.model.User;
import com.projectx.spring.hands.on.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findByUserName(userName);

    optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return optionalUser.map(CustomUserDetails::new).get();
  }
}
