package com.projectx.spring.hands.on.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.projectx.spring.hands.on.controller"))
        .paths(PathSelectors.regex("/user.*"))
        .build()
        .apiInfo(metaInfo());
  }

  private ApiInfo metaInfo() {

    return new ApiInfo(
        "User Registration",
        "Register a user to the system",
        "1.0",
        "https://projectx/terms",
        new Contact("Project-X", "https://projectx", "projectx@gmail.com"),
        "copy rights reserved @ Project-X",
        "https://projectx/license",
        new ArrayList<>());
  }
}
