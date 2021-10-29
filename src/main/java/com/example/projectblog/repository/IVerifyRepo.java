package com.example.projectblog.repository;

import com.example.projectblog.model.VerifiAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerifyRepo extends JpaRepository<VerifiAccount, Long> {
}
