package com.example.projectblog.service.account;

import com.example.projectblog.model.Account;
import com.example.projectblog.service.IGeneralService;

public interface IAccountService extends IGeneralService<Account> {
  Account loadUserByEmail(String email);


  boolean checkLogin(Account account);

  boolean add(Account account);
}
