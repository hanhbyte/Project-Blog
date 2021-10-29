package com.example.projectblog.dto;

import com.example.projectblog.model.Image;
import lombok.Data;

@Data
public class LoginAccount {

  private Long id;
  private String fullName;
  private String token;
  private Image avatar;
}
