package com.projectx.spring.hands.on.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Api(value = "user details")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(value = "Default generated ID")
  private Long id;

  @ApiModelProperty(value = "Name of the user")
  private String name;
  @ApiModelProperty(value = "Designation of the user")
  private String designation;
  @ApiModelProperty(value = "Salary of the user")
  private Double salary;
}
