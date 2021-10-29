package com.example.projectblog.service.verifi;

import com.example.projectblog.model.VerifiAccount;
import com.example.projectblog.repository.IVerifyRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifiService implements IVerifiService {

  @Autowired
  IVerifyRepo verifyRepo;


  public VerifiAccount add(VerifiAccount verifiAccount) {
    return verifyRepo.save(verifiAccount);
  }

  @Override
  public Iterable<VerifiAccount> findAll() {
    return null;
  }

  @Override
  public Optional<VerifiAccount> findById(Long id) {
    return verifyRepo.findById(id);
  }

  @Override
  public void save(VerifiAccount verifiAccount) {

  }

  @Override
  public void remove(Long id) {

  }
}
