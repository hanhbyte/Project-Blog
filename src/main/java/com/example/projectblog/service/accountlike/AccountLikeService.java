package com.example.projectblog.service.accountlike;

import com.example.projectblog.model.AccountLike;
import com.example.projectblog.repository.ILikeRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountLikeService implements IAccountLikeService{

  @Autowired
  private ILikeRepo likeRepo;
  @Override
  public Iterable<AccountLike> findAll() {
    return likeRepo.findAll();
  }

  @Override
  public Optional<AccountLike> findById(Long id) {
    return likeRepo.findById(id);
  }

  @Override
  public void save(AccountLike accountLike) {
    likeRepo.save(accountLike);
  }

  @Override
  public void remove(Long id) {
    likeRepo.deleteById(id);
  }



  @Override
  public AccountLike findByAccount_IdAndPost_Id(Long idAcc, Long idPost) {
    return likeRepo.findByAccount_IdAndPost_Id(idAcc,idPost);
  }

  @Override
  public void delete(AccountLike accountLike) {
    likeRepo.delete(accountLike);
  }
}
