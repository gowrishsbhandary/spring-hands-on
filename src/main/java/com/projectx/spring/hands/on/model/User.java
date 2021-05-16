package com.projectx.spring.hands.on.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@Api(value = "User details")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  @ApiModelProperty(hidden = true)
  private Long id;

  @ApiModelProperty(value = "Email id of the user")
  @Column(name = "email_id")
  private String emailId;

  @ApiModelProperty(value = "User name of the user")
  @Column(name = "user_name")
  private String userName;

  @ApiModelProperty(hidden = true)
  @Column(name = "password")
  private String password;

  @ApiModelProperty(value = "First name of the user")
  @Column(name = "first_name")
  private String firstName;

  @ApiModelProperty(value = "Last name of the user")
  @Column(name = "last_name")
  private String lastName;

  @ApiModelProperty(value = "Is user active")
  @Column(name = "active")
  private Integer active;

  @ApiModelProperty(value = "Designation of the user")
  @Column(name = "designation")
  private String designation;

  @ApiModelProperty(value = "Salary of the user")
  @Column(name = "salary")
  private Double salary;

  @ApiModelProperty(hidden = true)
  @Column(name = "create_time")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "User roles")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public User() {}

  public User(User user) {
    this.id = user.getId();
    this.emailId = user.getEmailId();
    this.userName = user.getUserName();
    this.password = user.getPassword();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.active = user.getActive();
    this.designation = user.getDesignation();
    this.salary = user.getSalary();
    this.createTime = user.getCreateTime();
    this.roles = user.getRoles();
  }
}
