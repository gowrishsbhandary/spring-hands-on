package com.projectx.spring.hands.on.configuration.security;

import com.projectx.spring.hands.on.repository.UserRepository;
import com.projectx.spring.hands.on.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(customUserDetailsService).passwordEncoder(getPasswordEncoder());
  }

  private PasswordEncoder getPasswordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence charSequence) {
        return charSequence.toString();
      }

      @Override
      public boolean matches(CharSequence charSequence, String s) {
        return true;
      }
    };
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        .antMatchers("/h2-console/**")
        .permitAll()
        .antMatchers("/user/load", "/user/getAll")
        .hasRole("ADMIN")
        .antMatchers("/user/register", "/user/{userName}")
        .hasRole("USER")
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .formLogin()
        .permitAll();
    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().disable();
  }
}
