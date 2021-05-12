package com.projectx.spring.hands.on.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("Gowrish")
        .password("{noop}User@123")
        .roles("USER")
        .and()
        .withUser("Admin")
        .password("{noop}Admin@123")
        .roles("ADMIN");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeRequests()
            .antMatchers("/user/load", "/user/getAll").hasRole("ADMIN")
            .antMatchers("/user/register", "/user/{name}").hasRole("USER")
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .httpBasic();
    httpSecurity.csrf().disable();
  }
}
