package com.example.projectblog.repository;

import com.example.projectblog.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepo extends JpaRepository<Account, Long> {
  Account findByEmail(String email);

}
