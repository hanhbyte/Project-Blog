package com.example.projectblog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Email
  private String email;
  private String password;
  private String re_password;
  private String phoneNumber;
  private Boolean enable;
  @NotBlank
  private String fullName;
  private Date dateOfBirth;
  private String address;
  @ManyToOne
  private Image avatar;

  @ManyToOne
  private AppRole role;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    return authorities;
  }
}
