package com.projectx.spring.hands.on.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
@Api(value = "User role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  @ApiModelProperty(hidden = true)
  private Long roleId;

  @ApiModelProperty(value = "User role")
  @Column(name = "role")
  private String role;
}
