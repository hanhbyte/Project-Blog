package com.example.projectblog.api;

import com.example.projectblog.service.account.IAccountService;
import com.example.projectblog.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin( origins = "*")
public class AdminController {

  @Autowired
  IAccountService accountService;
  @Autowired
  JwtService jwtService;
}
