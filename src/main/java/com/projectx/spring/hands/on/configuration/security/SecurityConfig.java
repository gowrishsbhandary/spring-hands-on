package com.projectx.spring.hands.on.configuration.security;

import com.projectx.spring.hands.on.repository.UserRepository;
import com.projectx.spring.hands.on.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] AUTH_WHITELIST = {
    "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/h2-console/**", "/user/register"
  };
  private static final String[] AUTH_ADMIN = {"/user/load", "/user/getAll"};
  private static final String[] AUTH_USER = {"/user/{userName}"};
  private static final String ROLE_ADMIN = "ADMIN";
  private static final String ROLE_USER = "USER";

  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(customUserDetailsService).passwordEncoder(getPasswordEncoder());
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        .antMatchers(AUTH_WHITELIST)
        .permitAll()
        .antMatchers(AUTH_ADMIN)
        .hasRole(ROLE_ADMIN)
        .antMatchers(AUTH_USER)
        .hasRole(ROLE_USER)
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .formLogin()
        .permitAll();
    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().disable();
  }
}
