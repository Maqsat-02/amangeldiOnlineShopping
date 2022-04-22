package com.example.onlineshopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String fullName;

  private String address;

  private Long balance;

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @OneToOne(mappedBy = "user")
  @JsonManagedReference(value = "order")
  @JsonIgnore
  private Order order;

  @ElementCollection(fetch = FetchType.EAGER)
  List<AppUserRole> appUserRoles;

  public AppUser(String fullName, Long balance, String username, String password, List<AppUserRole> appUserRoles) {
    this.fullName = fullName;
    this.balance = balance;
    this.username = username;
    this.password = password;
    this.appUserRoles = appUserRoles;
  }
}
